package com.alatoo.reshu_ort.services.result;

import com.alatoo.reshu_ort.dto.ResultDTO;
import com.alatoo.reshu_ort.entities.Result;
import com.alatoo.reshu_ort.entities.User;
import com.alatoo.reshu_ort.exceptions.ApiException;
import org.springframework.http.HttpStatusCode;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ResultService {
    List<ResultDTO> findAllUserResults();
    ResultDTO saveResult(ResultDTO dto);
    void deleteResult(Long id);
    ResultDTO getResultById(Long id);
    List<ResultDTO> findAllResultsByTestId(Long id);
}
