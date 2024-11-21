package com.alatoo.reshu_ort.services.answer;

import com.alatoo.reshu_ort.dto.AnswerDTO;
import com.alatoo.reshu_ort.entities.Answer;
import com.alatoo.reshu_ort.exceptions.ApiException;
import org.springframework.http.HttpStatusCode;

import java.util.List;
import java.util.stream.Collectors;

public interface AnswerService {
    public List<AnswerDTO> getAllAnswersOfQuestion(Long id);
    public AnswerDTO saveAnswer(AnswerDTO dto);
    public void deleteAnswer(Long id);
}
