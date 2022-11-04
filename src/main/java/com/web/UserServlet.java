package com.web;

import com.database.DBException;
import com.database.entity.UserEntity;
import com.database.repository.UserDAO;
import com.database.repository.UserRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/UserService")
public class UserServlet extends HttpServlet {
    private UserDAO userRepository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
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
            case "LOAD":
                loadUser(request, response);
                break;
            case "PROFILE":
                loadUserProfile(request, response);
                break;
            case "CHANGE":
                changeUserProfile(request, response);
                break;
            case "DELETE":
                deleteUser(request, response);
                break;
            default:
                listUser(request, response);
        }
    }

    private void changeUserProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: read user data from form
        String userId = request.getParameter("userId");
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String passport = request.getParameter("passport");
        String password = request.getParameter("password");

        UserEntity newUser = new UserEntity(Integer.parseInt(userId), login, email, passport, password);

        userRepository.updateUserProfile(newUser);

        RequestDispatcher dispatcher = request.getRequestDispatcher("my_profile.jsp");
        dispatcher.forward(request, response);
    }

    private void updateUserByAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String passport = request.getParameter("passport");
        String password = request.getParameter("password");
        String userRoleId = request.getParameter("userRoleId");
        String userStateActive = request.getParameter("userState");

        UserEntity newUser = new UserEntity(Integer.parseInt(userId), login, email, passport, password,
                Integer.parseInt(userRoleId), Boolean.parseBoolean(userStateActive));

        userRepository.updateUserByAdmin(newUser);

        listUser(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");

        userRepository.deleteUserById(Integer.parseInt(userId));
        listUser(request, response);
    }


    private void loadUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");

        UserEntity userById = null;

        userById = userRepository.getUserById(Integer.parseInt(userId));


        request.setAttribute("USER_ID", userById);

        RequestDispatcher dispatcher = request.getRequestDispatcher("update_user.jsp");
        dispatcher.forward(request, response);

    }

    private void loadUserProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");

        UserEntity userById = null;

        userById = userRepository.getUserById(Integer.parseInt(userId));


        request.setAttribute("USER_ID", userById);

        RequestDispatcher dispatcher = request.getRequestDispatcher("update_user_profile.jsp");
        dispatcher.forward(request, response);

    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DBException {
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passport = request.getParameter("passport");

        UserEntity newUser = UserEntity.createUser(login, email, password, passport);

        userRepository.createUser(newUser);

        listUser(request, response);

    }

    public void listUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserEntity> userList = userRepository.getAllUser();

        request.setAttribute("user_list", userList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("user_list.jsp");
        dispatcher.forward(request, response);
    }
}
