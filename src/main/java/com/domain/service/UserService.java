package com.domain.service;

import com.database.entity.UserEntity;
import com.database.repository.BookingDAO;
import com.database.repository.BookingRepository;
import com.database.repository.UserDAO;
import com.database.repository.UserRepository;
import com.domain.mapper.UserMapper;
import com.domain.model.Booking;
import com.domain.model.User;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

public class UserService implements Service<User, Integer>, UserServiceForAdmin {

    private final UserDAO userRepository;
    private UserMapper mapper = Mappers.getMapper(UserMapper.class);

    public UserService() {
        this.userRepository = new UserRepository();
    }

    @Override
    public void create(User user) {
        UserEntity entityUser = mapper.userToUserEntity(user);
        userRepository.createUser(entityUser);
    }

    @Override
    public User read(Integer id) {
        return mapper.userEntityToUser(userRepository.getUserById(id));
    }

    @Override
    public List<User> readAll() {
        return userRepository.getAllUser().stream()
                .map(userEntity -> mapper.userEntityToUser(userEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void update(User user) {
        UserEntity entityUser = mapper.userToUserEntity(user);
        userRepository.updateUserProfile(entityUser);
    }

    @Override
    public void delete(User user) {
        userRepository.deleteUserById(user.getId());
    }

    @Override
    public User getUserForLogin(String login, String password) {
        UserEntity userEntity = userRepository.getUserForLogin(login, password);
        return mapper.userEntityToUser(userEntity);
    }

    @Override
    public void updateUserByAdmin(User user) {
        UserEntity entityUser = mapper.userToUserEntity(user);
        userRepository.updateUserByAdmin(entityUser);
    }

}
