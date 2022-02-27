package edu.school21.cinema.listeners;

import edu.school21.cinema.config.MyAppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyAppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("myAppContext", new AnnotationConfigApplicationContext(MyAppConfig.class));
        servletContext.setRequestCharacterEncoding("UTF-8");
    }
}
