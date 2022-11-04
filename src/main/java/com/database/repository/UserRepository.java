package com.database.repository;

import com.database.DataBaseConnection;
import com.database.entity.UserEntity;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements UserDAO {
    static final Logger logger = Logger.getLogger(UserRepository.class);

    private static final String CREATE_USER = "INSERT INTO user (login, email, password, passport) VALUES (?, ?, ?, ?)";

    private static final String FIND_ALL_USER = "SELECT * FROM user";

    private static final String FIND_USER_BY_ID = FIND_ALL_USER + " WHERE id = ?";

    private static final String FIND_USER_FOR_LOGIN = FIND_ALL_USER + " WHERE login = ? AND password = ?";

    private static final String UPDATE_USER_PROFILE = "UPDATE user SET login = ?, email = ?, passport =?, " +
            "password = ? WHERE id = ?";

    private static final String UPDATE_USER_STATE = "UPDATE user SET user_state = ? WHERE id = ?";

    private static final String UPDATE_USER_ROLE = "UPDATE user SET user_role_id = ? WHERE id = ?";

    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";

    @Override public void createUser(UserEntity user) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(CREATE_USER);
        ) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getPassport());
            statement.execute();
            logger.info("UserRepository create user");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("UserRepository got error while creating the user");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("UserRepository closed connection");
        }
    }


    @Override public List<UserEntity> getAllUser() {
        List<UserEntity> userList = new ArrayList<>();
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL_USER)
        ) {
            while (rs.next()) {
                UserEntity user = new UserEntity(rs.getInt("id"), rs.getString("login"),
                        rs.getString("email"), rs.getString("password"),
                        rs.getString("passport"), rs.getInt("user_role_id"),
                        rs.getBoolean("user_state"));
                userList.add(user);
            }
            logger.info("UserRepository find all users");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("UserRepository got error while get all users");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("UserRepository closed connection");
        }
        return userList;
    }

    @Override public UserEntity getUserById(int userId) {
        UserEntity user = null;
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID)
        ) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = new UserEntity(rs.getInt("id"), rs.getString("login"),
                        rs.getString("email"), rs.getString("passport"),
                        rs.getString("password"));
                user.setUserRoleId(rs.getInt("user_role_id"));
                user.setUserStateActive(rs.getBoolean("user_state"));
            }
            logger.info("UserRepository find user by id");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("UserRepository got error while getting the user by id");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("UserRepository closed connection");
        }
        return user;
    }

    @Override public UserEntity getUserForLogin(String login, String password) {
        UserEntity user = null;
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_USER_FOR_LOGIN)
        ) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = extractUserFromResultSet(rs);
            } else {
                user = new UserEntity(1, "1", "1", "1", "1");
            }
            logger.info("UserRepository find user for login");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("UserRepository got error while getting the user for login");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("UserRepository closed connection");
        }
        return user;
    }


    @Override public void updateUserProfile(UserEntity user) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_PROFILE)
        ) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassport());
            statement.setString(4, user.getPassword());
            statement.setInt(5, user.getId());
            statement.executeUpdate();
            logger.info("UserRepository update user profile");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("UserRepository got error while updating user profile");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("UserRepository closed connection");
        }
    }

    @Override public void updateUserByAdmin(UserEntity user) {
        updateUserProfile(user);
        updateUserRole(user);
        updateUserState(user);
    }

    @Override public void updateUserRole(UserEntity user) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_ROLE)
        ) {
            statement.setInt(1, user.getUserRoleId());
            statement.setInt(2, user.getId());
            statement.executeUpdate();
            logger.info("UserRepository update user role");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("UserRepository got error while updating user role");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("UserRepository closed connection");
        }
    }

    @Override public void updateUserState(UserEntity user) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_STATE)
        ) {
            statement.setBoolean(1, user.isUserStateActive());
            statement.setInt(2, user.getId());
            statement.executeUpdate();
            logger.info("UserRepository update user state");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("UserRepository got error while updating user state");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("UserRepository closed connection");
        }
    }


    @Override public void deleteUserById(int id) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_USER)
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("UserRepository delete user");
        } catch (SQLException throwable) {
            DataBaseConnection.rollback(connection);
            logger.error("UserRepository got error while deleting user");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("UserRepository closed connection");
        }
    }

    private UserEntity extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        UserEntity user = new UserEntity();
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassport(resultSet.getString("passport"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setUserRoleId(resultSet.getInt("user_role_id"));
        user.setUserStateActive(resultSet.getBoolean("user_state"));
        return user;
    }

}
