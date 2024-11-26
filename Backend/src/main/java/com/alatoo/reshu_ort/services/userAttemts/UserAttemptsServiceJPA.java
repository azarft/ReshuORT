package com.alatoo.reshu_ort.services.userAttemts;

import com.alatoo.reshu_ort.dto.UserAttemptDTO;
import com.alatoo.reshu_ort.entities.UserAttempt;
import com.alatoo.reshu_ort.exceptions.ApiException;
import com.alatoo.reshu_ort.mappers.UserAttemptsMapper;
import com.alatoo.reshu_ort.repositories.UserAttemptsRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserAttemptsServiceJPA implements UserAttemptsService {
    private final UserAttemptsRepository userAttemptsRepository;
    private final UserAttemptsMapper userAttemptsMapper;

    public UserAttemptsServiceJPA(UserAttemptsRepository userAttemptsRepository, UserAttemptsMapper userAttemptsMapper) {
        this.userAttemptsRepository = userAttemptsRepository;
        this.userAttemptsMapper = userAttemptsMapper;
    }

    @Transactional
    @Override
    public UserAttemptDTO saveAttempt(UserAttemptDTO attemptDTO) {
        UserAttempt attempt = userAttemptsMapper.userAttemptsDtoToUserAttempts(attemptDTO);
        UserAttempt savedUserAttempt = userAttemptsRepository.save(attempt);
        return userAttemptsMapper.userAttemptsToUserAttemptsDto(savedUserAttempt);
    }

    @Override
    public List<UserAttemptDTO> saveAttempts(List<UserAttemptDTO> attemptDTOs) {
        List<UserAttempt> attempts = attemptDTOs.stream()
                .map(userAttemptsMapper::userAttemptsDtoToUserAttempts)
                .collect(Collectors.toList());
        userAttemptsRepository.saveAll(attempts);
        return attemptDTOs;
    }


    @Override
    public List<UserAttemptDTO> findAllUserAttemptsOfResult(Long id) {
        List<UserAttempt> userAttempts = userAttemptsRepository.getUserAttemptsByResultResultId(id);
        return userAttempts.stream()
                .map(userAttemptsMapper::userAttemptsToUserAttemptsDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserAttemptDTO saveUserAttempts(UserAttemptDTO dto) {
        UserAttempt userAttempt = userAttemptsMapper.userAttemptsDtoToUserAttempts(dto);
        UserAttempt savedUserAttempt = userAttemptsRepository.save(userAttempt);
        return userAttemptsMapper.userAttemptsToUserAttemptsDto(savedUserAttempt);
    }

    @Override
    public void deleteUserAttempts(Long id) {
        if (!userAttemptsRepository.existsById(id)) {
            throw new ApiException("User attempt not found with id", HttpStatusCode.valueOf(409));
        }
        userAttemptsRepository.deleteById(id);
    }
}
