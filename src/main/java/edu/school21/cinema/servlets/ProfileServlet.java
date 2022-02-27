package edu.school21.cinema.servlets;

import edu.school21.cinema.models.User;
import edu.school21.cinema.repositories.AuthenticationsRepository;
import edu.school21.cinema.repositories.ImagesRepository;
import org.springframework.context.ApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private ApplicationContext myAppContext;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        myAppContext = (ApplicationContext)servletContext.getAttribute("myAppContext");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthenticationsRepository authRepository = myAppContext.getBean(AuthenticationsRepository.class);
        ImagesRepository imagesRepository = myAppContext.getBean(ImagesRepository.class);
        User user = (User)req.getSession().getAttribute("user");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/secure/Profile.jsp");

        req.getSession().setAttribute("authentications", authRepository.findAllByUserId(user.getId()));
        req.getSession().setAttribute("images", imagesRepository.findAllByUserId(user.getId()));
        requestDispatcher.forward(req, resp);
    }
}
