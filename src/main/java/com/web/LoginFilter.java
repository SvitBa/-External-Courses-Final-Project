package com.web;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        boolean isLogged = (session != null && session.getAttribute("user") != null);
        boolean isStaticResource = request.getRequestURI().startsWith("/css/");
        if (isLogged && isLoginPage(request.getRequestURI())) {
            request.getRequestDispatcher("/CarServlet").forward(servletRequest, servletResponse);
        } else if (!isLogged && isLoginRequired(request.getRequestURI())) {
            request.getRequestDispatcher("/").forward(servletRequest, servletResponse);
        } else if (isStaticResource) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletRequest.setAttribute("redirect", request.getRequestURL().toString());
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean isLoginRequired(String requestURI) {
        return !requestURI.equals("/carRentApp/") && !requestURI.equals("/carRentApp/LoginServlet");
    }

    private boolean isLoginPage(String requestURI) {
        return requestURI.equals("/carRentApp/");
    }


    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

}
