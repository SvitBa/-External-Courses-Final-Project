package com.database.repository;

import com.database.entity.UserEntity;

import java.util.List;

public interface UserDAO {

    void createUser(UserEntity user);

    List<UserEntity> getAllUser();

    UserEntity getUserById(int userId);

    UserEntity getUserForLogin(String login, String password);

    void updateUserProfile(UserEntity user);

    void updateUserByAdmin(UserEntity user);

    void updateUserRole(UserEntity user);

    void updateUserState(UserEntity user);

    void deleteUserById(int id);
}
