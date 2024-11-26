package com.alatoo.reshu_ort.services.result;

import com.alatoo.reshu_ort.dto.ResultDTO;
import com.alatoo.reshu_ort.entities.Result;
import com.alatoo.reshu_ort.entities.User;
import com.alatoo.reshu_ort.exceptions.ApiException;
import com.alatoo.reshu_ort.mappers.ResultMapper;
import com.alatoo.reshu_ort.repositories.ResultRepository;
import com.alatoo.reshu_ort.services.user.UserService;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResultServiceJPA implements ResultService {
    private final ResultRepository resultRepository;
    private final ResultMapper resultMapper;
    private final UserService userService;

    public ResultServiceJPA(ResultRepository resultRepository, ResultMapper resultMapper, UserService userService) {
        this.resultRepository = resultRepository;
        this.resultMapper = resultMapper;
        this.userService = userService;
    }

    @Override
    public List<ResultDTO> findAllUserResults() {
        User user = userService.getCurrentUser();
        List<Result> results = resultRepository.getResultsByUserId(user.getId());
        return results.stream()
                .map(resultMapper::resultToResultDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResultDTO saveResult(ResultDTO dto) {
        User user = userService.getCurrentUser();
        Result result = resultMapper.resultDtoToResult(dto);
        result.setAttemptDate(LocalDateTime.now());
        result.setUser(user);
        Result savedResult = resultRepository.save(result);
        return resultMapper.resultToResultDto(savedResult);
    }

    @Override
    public void deleteResult(Long id) {
        User user = userService.getCurrentUser();
        if (!resultRepository.existsByResultIdAndUserId(id, user.getId())) {
            throw new ApiException("Result not found with id", HttpStatusCode.valueOf(409));
        }
        resultRepository.deleteById(id);
    }

    @Override
    public ResultDTO getResultById(Long id) {
        User user = userService.getCurrentUser();
        Optional<Result> optionalResult = resultRepository.findByResultIdAndUserId(id, user.getId());
        Result result = optionalResult.orElseThrow(() -> new ApiException("Result not found with id", HttpStatusCode.valueOf(409)));
        return resultMapper.resultToResultDto(result);
    }

    @Override
    public List<ResultDTO> findAllResultsByTestId(Long id) {
        List<Result> results = resultRepository.getResultsByTestTestId(id);
        return results.stream()
                .map(resultMapper::resultToResultDto)
                .collect(Collectors.toList());
    }
}
