package com.alatoo.reshu_ort.services.userAttemts;


import com.alatoo.reshu_ort.dto.UserAttemptDTO;

import java.util.List;

public interface UserAttemptsService {
    public List<UserAttemptDTO> findAllUserAttemptsOfResult(Long id);
    public UserAttemptDTO saveUserAttempts(UserAttemptDTO dto);
    public void deleteUserAttempts(Long id);
}
