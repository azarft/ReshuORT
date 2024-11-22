package com.alatoo.reshu_ort.controllers;

import com.alatoo.reshu_ort.dto.UserAttemptDTO;
import com.alatoo.reshu_ort.services.userAttemts.UserAttemptsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserAttemptController {
    private final UserAttemptsService userAttemptsService;

    private final String USER_ATTEMPT_PATH = "/userattempts";
    private final String ID_PATH = "/{id}";

    public UserAttemptController(UserAttemptsService userAttemptsService) {
        this.userAttemptsService = userAttemptsService;
    }

    @GetMapping(USER_ATTEMPT_PATH)
    public List<UserAttemptDTO> getAllAnswers(@PathVariable Long id) {
        return userAttemptsService.findAllUserAttemptsOfResult(id);
    }

    @PostMapping(USER_ATTEMPT_PATH)
    public UserAttemptDTO saveAnswer(@RequestBody UserAttemptDTO dto) {
        return userAttemptsService.saveUserAttempts(dto);
    }

    @DeleteMapping(USER_ATTEMPT_PATH + ID_PATH)
    public void deleteAnswer(@PathVariable Long id) {
        userAttemptsService.deleteUserAttempts(id);
    }
}
