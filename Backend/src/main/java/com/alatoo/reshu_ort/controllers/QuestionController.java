package com.alatoo.reshu_ort.controllers;

import com.alatoo.reshu_ort.dto.QuestionDTO;
import com.alatoo.reshu_ort.services.question.QuestionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class QuestionController {
    private final QuestionService questionService;

    private final String QUESTION_PATH = "/questions";
    private final String ID_PATH = "/{id}";

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping(QUESTION_PATH + ID_PATH)
    public List<QuestionDTO> getAllQuestions(@PathVariable Long id) {
        return questionService.findAllQuestionsOfTest(id);
    }

    @PostMapping(QUESTION_PATH)
    public QuestionDTO saveQuestion(@RequestBody QuestionDTO dto) {
        return questionService.saveQuestion(dto);
    }

    @DeleteMapping(QUESTION_PATH + ID_PATH)
    public void deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
    }
}
