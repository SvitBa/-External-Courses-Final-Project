package com.data.repository;

import com.data.DataBaseConnection;
import com.data.DBException;
import com.data.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static final String CREATE_USER = "INSERT INTO user (login, email, password, passport) VALUES (?, ?, ?, ?)";

    private static final String FIND_ALL_USER = "SELECT * FROM user";

    private static final String FIND_USER_BY_ID = FIND_ALL_USER + " WHERE id = ?";

    private static final String FIND_USER_FOR_LOGIN = FIND_ALL_USER + " WHERE login = ? AND password = ?";

    private static final String FIND_ALL_USER_BY_ROLE = FIND_ALL_USER + " WHERE user_role_id = ?";

    private static final String FIND_ALL_USER_BY_STATE = FIND_ALL_USER + " WHERE user_state = ?";


    private static final String UPDATE_USER_PROFILE = "UPDATE user SET login = ?, email = ?, passport =?, " +
            "password = ? WHERE id = ?";

    private static final String UPDATE_USER_STATE = "UPDATE user SET user_state = ? WHERE id = ?";

    private static final String UPDATE_USER_ROLE = "UPDATE user SET user_role_id = ? WHERE id = ?";

    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";

    public static void createUser(User user) throws DBException {
        try (java.sql.Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER);
             ){

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getPassport());
            statement.execute();

        } catch (SQLException e) {
            throw new DBException("Create user FAIL", e);
        }
    }


    public static List<User> getAllUser() throws DBException {
        List<User> userList = new ArrayList<>();
        try (java.sql.Connection con = DataBaseConnection.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL_USER)
        ) {
            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("login"),
                        rs.getString("email"), rs.getString("password"),
                        rs.getString("passport"), rs.getInt("user_role_id"), rs.getBoolean("user_state"));
                userList.add(user);
            }

        } catch (SQLException e) {
            throw new DBException("FIND all user FAIL", e);
        }
        return userList;
    }

    public static User getUserById(int userId) throws DBException {

        User user = null;
        try (java.sql.Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(FIND_USER_BY_ID)
        ) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("id"), rs.getString("login"),
                        rs.getString("email"), rs.getString("password"),
                        rs.getString("passport"));
                user.setUserRoleId(rs.getInt("user_role_id"));
                user.setUserStateActive(rs.getBoolean("user_state"));
            }
        } catch (SQLException e) {
            throw new DBException("Get user by id FAIL", e);
        }
        return user;
    }

    public static User getUserForLogin(String login, String password) throws DBException {

        User user = null;
        try (java.sql.Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(FIND_USER_FOR_LOGIN)
        ) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("id"), rs.getString("login"),
                        rs.getString("email"), rs.getString("password"),
                        rs.getString("passport"));
                user.setUserRoleId(rs.getInt("user_role_id"));
                user.setUserStateActive(rs.getBoolean("user_state"));
            } else {
                user = new User (1, "1", "1", "1", "1");
            }
        } catch (SQLException e) {
            throw new DBException("Get user for login FAIL", e);
        }
        return user;
    }

    public static List<User> getUserByRole(int userRoleId) throws DBException {
        List<User> userList = new ArrayList<>();
        try (java.sql.Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(FIND_ALL_USER_BY_ROLE)
        ) {
            statement.setInt(1, userRoleId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("login"),
                        rs.getString("email"), rs.getString("password"),
                        rs.getString("passport"));
                user.setUserRoleId(rs.getInt("user_role"));
                user.setUserStateActive(rs.getBoolean("user_role_id"));
                userList.add(user);
            }

        } catch (SQLException e) {
            throw new DBException("FIND user by role FAIL", e);
        }
        return userList;
    }

    public static List<User> getUserByState(boolean userState) throws DBException {
        List<User> userList = new ArrayList<>();
        try (java.sql.Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(FIND_ALL_USER_BY_STATE)
        ) {
            statement.setBoolean(1, userState);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("login"),
                        rs.getString("email"), rs.getString("password"),
                        rs.getString("passport"));
                user.setUserRoleId(rs.getInt("user_role"));
                user.setUserStateActive(rs.getBoolean("user_role_id"));
                userList.add(user);
            }

        } catch (SQLException e) {
            throw new DBException("FIND user by state FAIL", e);
        }
        return userList;
    }


    public static User updateUserProfile(User user) {
        try (java.sql.Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(UPDATE_USER_PROFILE)
        ) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassport());
            statement.setString(4, user.getPassword());
            statement.setInt(5, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static User updateUserByAdmin(User user) {
        updateUserProfile(user);
        updateUserRole(user);
        updateUserState(user);
        return user;
    }

    public static User updateUserRole(User user) {
        try (java.sql.Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(UPDATE_USER_ROLE)
        ) {
            statement.setInt(1, user.getUserRoleId());
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static User updateUserState(User user) {
        try (java.sql.Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(UPDATE_USER_STATE)
        ) {
            statement.setBoolean(1, user.isUserStateActive());
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    public static void deleteUserById(int id) {
        try (java.sql.Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_USER)
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }


}
