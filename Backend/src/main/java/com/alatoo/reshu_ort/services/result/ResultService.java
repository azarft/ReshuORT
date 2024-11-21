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
    public List<ResultDTO> findAllUserResultsBy();
    public ResultDTO saveResult(ResultDTO dto);
    public void deleteResult(Long id);
    public ResultDTO getResultById(Long id);
}
