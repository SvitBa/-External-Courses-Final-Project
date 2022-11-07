package com.web.mapper;

import com.domain.model.User;
import com.web.model.UserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapperWeb {
    UserDTO userToUserDTO (User user);
    User userDTOToUser(UserDTO userDTO);
}
