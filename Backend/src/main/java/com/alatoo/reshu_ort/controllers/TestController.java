package com.alatoo.reshu_ort.controllers;

import com.alatoo.reshu_ort.entities.Test;
import com.alatoo.reshu_ort.exceptions.ApiException;
import com.alatoo.reshu_ort.services.test.TestService;
import com.alatoo.reshu_ort.dto.TestDTO;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class TestController {
    private final TestService testService;

    private final String TEST_PATH = "/tests";
    private final String ID_PATH = "/{id}";

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping(TEST_PATH)
    public List<TestDTO> getAllTests() {
        return testService.findAllTests();
    }

    @GetMapping(TEST_PATH + ID_PATH)
    public TestDTO getTestById(@PathVariable Long id) {
        return testService.findTestById(id).orElseThrow(() -> new ApiException("Album not found with id: " + id, HttpStatusCode.valueOf(409)));
    }

    @PostMapping(TEST_PATH)
    public TestDTO createTest(@RequestBody TestDTO dto) {
        return testService.saveTest(dto);
    }

    @PutMapping(TEST_PATH + ID_PATH)
    public TestDTO updateTest(@PathVariable Long id, @RequestBody TestDTO testDTO) {
        testService.findTestById(id).orElseThrow(() -> new ApiException("Test not found with id: " + id, HttpStatusCode.valueOf(409)));
        testDTO.setTestId(id);
        return testService.saveTest(testDTO);
    }

    @DeleteMapping(TEST_PATH + ID_PATH)
    public void deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
    }

    @GetMapping(TEST_PATH + "/my")
    public List<TestDTO> getUsersTests() {
        return testService.findUsersTests();
    }
}
