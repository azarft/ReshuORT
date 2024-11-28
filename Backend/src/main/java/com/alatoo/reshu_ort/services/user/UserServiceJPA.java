package com.alatoo.reshu_ort.services.user;

import com.alatoo.reshu_ort.dto.UserDTO;
import com.alatoo.reshu_ort.dto.authorization.AuthRegistrationDTO;
import com.alatoo.reshu_ort.entities.Test;
import com.alatoo.reshu_ort.entities.User;
import com.alatoo.reshu_ort.enums.Role;
import com.alatoo.reshu_ort.exceptions.ApiException;
import com.alatoo.reshu_ort.mappers.UserMapper;
import com.alatoo.reshu_ort.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceJPA implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceJPA(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserDTO> findUserByID(Long id) {
        Optional<User> optionalUserEntity = userRepository.findById(id);
        User user = optionalUserEntity.orElseThrow(() -> new ApiException("User not found with id" + id , HttpStatusCode.valueOf(409)));
        return Optional.of(userMapper.userToUserDto(user));
    }

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
            throw new ApiException("Error while user creating", HttpStatusCode.valueOf(400));
        }
    }

    @Override
    public UserDTO register(AuthRegistrationDTO authRegistrationDTO) {
        User user = userMapper.authRegistrationDtoToUserEntity(authRegistrationDTO);
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(authRegistrationDTO.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return userMapper.userToUserDto(savedUser);
    }

    @Override
    public void deleteUser() {
        User user = getCurrentUser();
        userRepository.deleteById(user.getId());
    }

    @Override
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ApiException("Test not found with id: " + id, HttpStatusCode.valueOf(409));
        }
        userRepository.deleteById(id);
    }


    @Override
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof User userDetails) {
            Optional<User> optionalUser = userRepository.findById(userDetails.getId());
            User user = optionalUser.orElseThrow(() -> new ApiException("User not found with id " + userDetails.getId(), HttpStatusCode.valueOf(409)));
            return user;
        } else {
            return null;
        }
    }


    private Role getUsersRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof User userDetails) {
            Optional<User> optionalUser = userRepository.findById(userDetails.getId());
            User user = optionalUser.orElseThrow(() -> new ApiException("User not found with id " + userDetails.getId(), HttpStatusCode.valueOf(409)));
            return user.getRole();
        } else {
            return null;
        }
    }

    @Override
    public boolean isUserAdmin() {
        return getUsersRole() == Role.ROLE_ADMIN;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

}
