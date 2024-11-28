package com.alatoo.reshu_ort.controllers;

import com.alatoo.reshu_ort.dto.QuestionDTO;
import com.alatoo.reshu_ort.dto.ResultDTO;
import com.alatoo.reshu_ort.dto.TestDTO;
import com.alatoo.reshu_ort.dto.UserDTO;
import com.alatoo.reshu_ort.entities.Question;
import com.alatoo.reshu_ort.exceptions.ApiException;
import com.alatoo.reshu_ort.services.question.QuestionService;
import com.alatoo.reshu_ort.services.result.ResultService;
import com.alatoo.reshu_ort.services.test.TestService;
import com.alatoo.reshu_ort.services.user.UserService;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final String USER_PATH = "/users";
    private final String RESULT_PATH = "/results";
    private final String TEST_PATH = "/tests";
    private final String QUESTION_PATH = "/questions";
    private final String ID_PATH = "/{id}";

    private final UserService userService;
    private final TestService testService;
    private final QuestionService questionService;
    private final ResultService resultService;


    public AdminController(UserService userService, TestService testService, QuestionService questionService, ResultService resultService) {
        this.userService = userService;
        this.testService = testService;
        this.questionService = questionService;
        this.resultService = resultService;
    }

    @GetMapping(USER_PATH)
    public List<UserDTO> getAllUsers() {
        if (!userService.isUserAdmin()) {
            throw new ApiException("Forbidden", HttpStatusCode.valueOf(403));
        }
        return userService.getAllUsers();
    }

    @GetMapping(RESULT_PATH)
    public List<ResultDTO> getAllResults() {
        if (!userService.isUserAdmin()) {
            throw new ApiException("Forbidden", HttpStatusCode.valueOf(403));
        }
        return resultService.findAllResults();
    }

    @GetMapping(QUESTION_PATH)
    public List<QuestionDTO> getAllQuestions() {
        if (!userService.isUserAdmin()) {
            throw new ApiException("Forbidden", HttpStatusCode.valueOf(403));
        }
        return questionService.findAllQuestions();
    }

    @DeleteMapping(USER_PATH + ID_PATH)
    public void deleteUser(@PathVariable Long id) {
        if (!userService.isUserAdmin()) {
            throw new ApiException("Forbidden", HttpStatusCode.valueOf(403));
        }
        userService.deleteUserById(id);
    }

    @DeleteMapping(TEST_PATH + ID_PATH)
    public void deleteTest(@PathVariable Long id) {
        if (!userService.isUserAdmin()) {
            throw new ApiException("Forbidden", HttpStatusCode.valueOf(403));
        }
        testService.deleteTestById(id);
    }

    @DeleteMapping(RESULT_PATH + ID_PATH)
    public void deleteResult(@PathVariable Long id) {
        if (!userService.isUserAdmin()) {
            throw new ApiException("Forbidden", HttpStatusCode.valueOf(403));
        }
        resultService.deleteResultById(id);
    }
}