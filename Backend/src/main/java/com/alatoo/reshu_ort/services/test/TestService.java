package com.alatoo.reshu_ort.services.test;

import com.alatoo.reshu_ort.dto.TestDTO;

import java.util.List;
import java.util.Optional;

public interface TestService {
    List<TestDTO> findAllTests();
    Optional<TestDTO> findTestById(Long id);
    TestDTO saveTest(TestDTO dto);
    void deleteTest(Long id);
    List<TestDTO> findUsersTests();
    void deleteTestById(Long id);
}
