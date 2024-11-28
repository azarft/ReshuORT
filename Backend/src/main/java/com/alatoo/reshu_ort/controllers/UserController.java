package com.alatoo.reshu_ort.controllers;

import com.alatoo.reshu_ort.dto.UserDTO;
import com.alatoo.reshu_ort.exceptions.ApiException;
import com.alatoo.reshu_ort.services.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Log4j2
public class UserController {
    private final String USER_PATH = "/users";
    private final String ID_PATH = "/{id}";

    private final UserService userService;

    @GetMapping(USER_PATH + ID_PATH)
    public UserDTO getUserById(@PathVariable Long id) {
        if (!userService.isUserAdmin()) {
            throw new ApiException("Forbidden: " + id, HttpStatus.FORBIDDEN);
        }
        return userService.findUserByID(id).orElseThrow(() -> new ApiException("User not found with id" + id , HttpStatusCode.valueOf(409)));
    }

}
