package edu.school21.cinema.servlets;

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

@MultipartConfig()
@WebServlet("/images")
public class ImagesServlet extends HttpServlet {

    private ApplicationContext myAppContext;
    private String storagePath;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        myAppContext = (ApplicationContext)servletContext.getAttribute("myAppContext");
        storagePath = myAppContext.getEnvironment().getProperty("storage.path");

        if (!storagePath.endsWith("/")) {
            storagePath += "/";
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UsersService usersService = myAppContext.getBean(UsersService.class);
        ImagesRepository imagesRepository = myAppContext.getBean(ImagesRepository.class);

        try {
            usersService.saveImage((User)req.getSession().getAttribute("user"), req.getPart("image"), storagePath, imagesRepository);
        } catch (IOException e) {
            throw new UsersServiceException(UsersServiceExceptionEnum.USER_SAVE_ERROR);
        }
        resp.sendRedirect("/profile");
    }
}
