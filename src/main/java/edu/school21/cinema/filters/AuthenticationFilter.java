package edu.school21.cinema.filters;


import edu.school21.cinema.models.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/profile", "/signIn", "/signUp", "/images/*"})
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        User user = (User)((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        String urlPattern = ((HttpServletRequest) servletRequest).getServletPath();
        RequestDispatcher requestDispatcher;

        if (user != null && (urlPattern.equals("/signIn") || urlPattern.equals("/signUp"))) {
            ((HttpServletResponse)servletResponse).sendRedirect("/profile");
        }
        else if (user == null && (!urlPattern.equals("/signIn") && !urlPattern.equals("/signUp"))) {
            requestDispatcher = servletRequest.getRequestDispatcher("/WEB-INF/jsp/secure/errorPages/403.jsp");
            requestDispatcher.forward(servletRequest, servletResponse);
        }
        else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
