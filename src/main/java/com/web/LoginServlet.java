package com.web;

import com.data.DBException;
import com.data.entity.User;
import com.data.repository.UserRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: read user data from form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.isBlank() || password == null || username.isBlank()) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }

        User user = null;

        // step 2: find user in db
        try {
            user = UserRepository.getUserForLogin(username, password);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        if (user.getId() == 1) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        } else {

            // step3: place user in request attribute
            request.setAttribute("USER_ID", user.getId());

            //step 4: send back to the search page
            RequestDispatcher dispatcher = request.getRequestDispatcher("CarServlet");
            dispatcher.forward(request, response);
        }
    }
}
