package com.alatoo.reshu_ort.services.userAttemts;


import com.alatoo.reshu_ort.dto.UserAttemptDTO;
import com.alatoo.reshu_ort.entities.UserAttempt;

import java.util.List;

public interface UserAttemptsService {
    List<UserAttemptDTO> findAllUserAttemptsOfResult(Long id);
    UserAttemptDTO saveUserAttempts(UserAttemptDTO dto);
    void deleteUserAttempts(Long id);
    UserAttemptDTO saveAttempt(UserAttemptDTO attemptDTO);
    List<UserAttemptDTO> saveAttempts(List<UserAttemptDTO> attemptDTOs);
}
