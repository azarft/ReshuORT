package com.alatoo.reshu_ort.mappers;

import com.alatoo.reshu_ort.dto.UserDTO;
import com.alatoo.reshu_ort.dto.authorization.AuthRegistrationDTO;
import com.alatoo.reshu_ort.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    User userDtoToUser(UserDTO dto);
    User authRegistrationDtoToUserEntity(AuthRegistrationDTO dto);
    UserDTO userToUserDto(User user);
}
