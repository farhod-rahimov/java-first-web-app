package edu.school21.cinema.servlets;

import edu.school21.cinema.repositories.UsersRepository;
import edu.school21.cinema.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/signUp")
public class SignUpServlet extends HttpServlet {

    private ApplicationContext myAppContext;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        myAppContext = (ApplicationContext)servletContext.getAttribute("myAppContext");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher reqRequestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/secure/SignUp.jsp");
        reqRequestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UsersService usersService = myAppContext.getBean(UsersService.class);
        UsersRepository usersRepository = myAppContext.getBean(UsersRepository.class);
        PasswordEncoder passwordEncoder = myAppContext.getBean(PasswordEncoder.class);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/secure/SignUpSucceeded.jsp");

        usersService.signUp(req, usersRepository, passwordEncoder);
        requestDispatcher.forward(req, resp);
    }
}
