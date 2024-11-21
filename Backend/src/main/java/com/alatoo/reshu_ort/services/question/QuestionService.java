package com.alatoo.reshu_ort.services.question;


import com.alatoo.reshu_ort.dto.QuestionDTO;
import com.alatoo.reshu_ort.entities.Question;

import java.util.List;
import java.util.stream.Collectors;

public interface QuestionService {
    public List<QuestionDTO> findAllQuestionsOfTest(Long id);
    public QuestionDTO saveQuestion(QuestionDTO dto);
    public void deleteQuestion(Long id);
}
