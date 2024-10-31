package com.alatoo.reshu_ort.controllers;

import com.alatoo.reshu_ort.dto.UserDTO;
import com.alatoo.reshu_ort.services.user.UserService;
import com.alatoo.reshu_ort.services.user.UserServiceJPA;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Log4j2
public class UserController {
    private final String USER_PATH = "/user";

    private final UserService userService;


    @PostMapping(USER_PATH)
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

}
