package com.servlet.example;

import com.db.DBException;
import com.db.UserImpl;
import com.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {


    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        //read command
        String theCommand = request.getParameter("command");

        if (theCommand == null) {
            theCommand = "LIST";
        }

        // route appropriate method
        switch (theCommand) {
            case "LIST":
                listUser(request, response);
                break;
            case "ADD":
                try {
                    addUser(request, response);
                } catch (DBException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "LOAD":
                loadUser(request, response);
                break;
            case "UPDATE":
                updateUserByAdmin(request, response);
                break;
            case "DELETE":
                deleteUser(request, response);
                break;
            default:
                listUser(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String theCommand = request.getParameter("command");
        if (theCommand == null) {
            theCommand = "LIST";
        }
        // route appropriate method
        switch (theCommand) {
            case "ADD":
                try {
                    addUser(request, response);
                } catch (DBException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "UPDATE":
                updateUserByAdmin(request, response);
                break;
            case "UPROFILE":
                updateUserProfile(request, response);
                break;
            case "DELETE":
                deleteUser(request, response);
                break;
            default:
                listUser(request, response);
        }
    }

    private void updateUserProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: read user data from form
        String userId = request.getParameter("userId");
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String passport = request.getParameter("passport");
        String password = request.getParameter("password");

        // step2: create new user object
        User newUser = new User(Integer.parseInt(userId), login, email, passport, password);

        // step3: update db
        UserImpl.updateUserProfile(newUser);

        // step4: send back to the profile page
//        listUser(request, response);
    }

    private void updateUserByAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: read user data from form
        String userId = request.getParameter("userId");
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String passport = request.getParameter("passport");
        String password = request.getParameter("password");
        String userRoleId = request.getParameter("userRoleId");
        String userStateActive = request.getParameter("userState");

        // step2: create new user object
        User newUser = new User(Integer.parseInt(userId), login, email, passport, password,
                Integer.parseInt(userRoleId), Boolean.parseBoolean(userStateActive));

        // step3: update db
        UserImpl.updateUserByAdmin(newUser);

        // step4: send back to the list page
        listUser(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: read user id from data
        String userId = request.getParameter("userId");

        // step2: delete user from db
        UserImpl.deleteUserById(Integer.parseInt(userId));

        // step3: send back to the list page
        listUser(request, response);
    }


    private void loadUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: read user id from data
        String userId = request.getParameter("userId");

        // step2: get user from db
        User userById = null;
        try {
            userById = UserImpl.getUserById(Integer.parseInt(userId));
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        // step3: place user in request attribute
        request.setAttribute("USER_ID", userById);

        // step4: send back jsp update page
        RequestDispatcher dispatcher = request.getRequestDispatcher("update_user.jsp");
        dispatcher.forward(request, response);

    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DBException {
        // step1: read user info from data
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passport = request.getParameter("passport");

        // step2: create new user object
        User newUser = User.createUser(login, email, password, passport);

        // step3: add user to db
        UserImpl.createUser(newUser);

        // step4: send back to the list page
        listUser(request, response);

    }

    public void listUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step 1: get users from helper class from db
        List<User> userList = null;
        try {
            userList = UserImpl.getAllUser();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        // step 2: add car_model to the request object
        request.setAttribute("user_list", userList);

        // step 3: get request dispatcher = send to the jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("user_list.jsp");

        // step 4: forward call jsp
        dispatcher.forward(request, response);


    }
}
