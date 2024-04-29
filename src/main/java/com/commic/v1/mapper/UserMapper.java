package com.commic.v1.mapper;

import com.commic.v1.dto.UserDTO;
import com.commic.v1.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserResponeDTO(User user);
    User toUserResponseEntity(UserDTO userDTO);
}
