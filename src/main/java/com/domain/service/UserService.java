package com.domain.service;

import com.database.entity.UserEntity;
import com.database.repository.UserDAO;
import com.database.repository.UserRepository;
import com.domain.model.User;

import java.util.List;

public class UserService implements Service<User, Integer>  {

    private final UserDAO userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    @Override
    public User create(User user) {
        UserEntity entityUser = new UserEntity();
        userRepository.createUser(entityUser);
        return null;
    }

    @Override
    public User read(Integer id) {
        return null;
    }

    @Override
    public List<User> readAll() {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(User user) {

    }
}
