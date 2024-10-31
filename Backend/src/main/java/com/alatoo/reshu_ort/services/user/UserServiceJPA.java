package com.alatoo.reshu_ort.services.user;

import com.alatoo.reshu_ort.dto.UserDTO;
import com.alatoo.reshu_ort.entities.User;
import com.alatoo.reshu_ort.exceptions.ApiException;
import com.alatoo.reshu_ort.mappers.UserMapper;
import com.alatoo.reshu_ort.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class UserServiceJPA implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = userMapper.userDtoToUser(userDTO);
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }
        try {
            User savedUser = userRepository.save(user);
            return userMapper.userToUserDto(savedUser);
        } catch (DataIntegrityViolationException e) {
            throw new ApiException("User " + userDTO.getUsername() + " is already exists", HttpStatusCode.valueOf(409));
        } catch (Exception e) {
            log.error("Error", e);
            throw new ApiException("Error while user creating", HttpStatusCode.valueOf(400));
        }

    }
}
