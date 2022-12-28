package com.web;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response)throws IOException {
        request.getSession().setAttribute("user", null);
        request.getSession().setAttribute("booking_list", null);
        request.getSession().setAttribute("invoice_list", null);
        response.sendRedirect(request.getContextPath() + "/");
    }


}
