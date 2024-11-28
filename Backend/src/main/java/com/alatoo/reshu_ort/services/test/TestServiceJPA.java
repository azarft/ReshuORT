package com.alatoo.reshu_ort.services.test;

import com.alatoo.reshu_ort.dto.TestDTO;
import com.alatoo.reshu_ort.entities.Test;
import com.alatoo.reshu_ort.entities.User;
import com.alatoo.reshu_ort.exceptions.ApiException;
import com.alatoo.reshu_ort.mappers.TestMapper;
import com.alatoo.reshu_ort.repositories.TestRepository;
import com.alatoo.reshu_ort.services.user.UserService;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestServiceJPA implements TestService {
    private final TestRepository testRepository;
    private final TestMapper testMapper;
    private final UserService userService;

    public TestServiceJPA(TestRepository testRepository, TestMapper testMapper, UserService userService) {
        this.testRepository = testRepository;
        this.testMapper = testMapper;
        this.userService = userService;
    }

    @Override
    public List<TestDTO> findAllTests() {
        List<Test> tests = testRepository.findAll();
        return tests.stream()
                .map(testMapper::testToTestDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TestDTO> findTestById(Long id) {
        Optional<Test> optionalTest = testRepository.findById(id);
        Test test = optionalTest.orElseThrow(() -> new ApiException("Test not found with id: " + id, HttpStatusCode.valueOf(409)));
        return Optional.of(testMapper.testToTestDto(test));
    }

    @Override
    public TestDTO saveTest(TestDTO dto) {
        User user = userService.getCurrentUser();
        Test test = testMapper.testDtoToTest(dto);
        test.setCreatedBy(user);
        Test savedTest = testRepository.save(test);
        return testMapper.testToTestDto(savedTest);
    }

    @Override
    public void deleteTest(Long id) {
        User user = userService.getCurrentUser();
        if (!testRepository.existsByTestIdAndCreatedById(id, user.getId())) {
            throw new ApiException("Test not found with id: " + id, HttpStatusCode.valueOf(409));
        }
        testRepository.deleteById(id);
    }

    @Override
    public void deleteTestById(Long id) {
        if (!testRepository.existsById(id)) {
            throw new ApiException("Test not found with id: " + id, HttpStatusCode.valueOf(409));
        }
        testRepository.deleteById(id);
    }

    @Override
    public List<TestDTO> findUsersTests() {
        User user = userService.getCurrentUser();
        List<Test> tests = testRepository.findByCreatedById(user.getId());
        return tests.stream()
                .map(testMapper::testToTestDto)
                .collect(Collectors.toList());
    }
}
