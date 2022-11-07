package com.domain.mapper;

import com.database.entity.UserEntity;
import com.domain.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserEntity userToUserEntity (User user);
    User userEntityToUser(UserEntity userEntity);
}
