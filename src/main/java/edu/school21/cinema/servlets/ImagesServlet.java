package edu.school21.cinema.servlets;

import edu.school21.cinema.models.Image;
import edu.school21.cinema.models.User;
import edu.school21.cinema.repositories.ImagesRepository;
import edu.school21.cinema.services.UsersService;
import edu.school21.cinema.services.UsersServiceException;
import edu.school21.cinema.services.UsersServiceExceptionEnum;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Optional;

@MultipartConfig()
@WebServlet("/images/*")
public class ImagesServlet extends HttpServlet {

    private ApplicationContext myAppContext;
    private UsersService usersService;
    private ImagesRepository imagesRepository;
    private String storagePath;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        myAppContext = (ApplicationContext)servletContext.getAttribute("myAppContext");
        usersService = myAppContext.getBean(UsersService.class);
        imagesRepository = myAppContext.getBean(ImagesRepository.class);
        storagePath = myAppContext.getEnvironment().getProperty("storage.path");

        if (!storagePath.endsWith("/")) {
            storagePath += "/";
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User)req.getSession().getAttribute("user");
        String uniqueName = getImageUniqueName(req.getPathInfo());
        Optional<Image> image = imagesRepository.findByUniqueNameAndUserId(uniqueName, user.getId());

        if (image.isPresent()) {
            try {
                usersService.sendImage(resp.getOutputStream(), image.get().getImagePath());
            } catch (IOException e) {
                throw new UsersServiceException(UsersServiceExceptionEnum.IMAGE_SEND_ERROR);
            }
        }
        else {
            req.getRequestDispatcher("/WEB-INF/jsp/secure/errorPages/404.jsp").forward(req, resp);
        }
    }

    private String getImageUniqueName(String uri) {
        int index = uri.lastIndexOf('/');
        return index < 0 ? "" : uri.substring(++index);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            usersService.saveImage((User)req.getSession().getAttribute("user"), req.getPart("image"), storagePath, imagesRepository);
        } catch (IOException e) {
            throw new UsersServiceException(UsersServiceExceptionEnum.IMAGE_SAVE_ERROR);
        }
        resp.sendRedirect("/profile");
    }
}
