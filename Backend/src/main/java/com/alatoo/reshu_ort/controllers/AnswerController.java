package com.alatoo.reshu_ort.controllers;

import com.alatoo.reshu_ort.dto.AnswerDTO;
import com.alatoo.reshu_ort.dto.QuestionDTO;
import com.alatoo.reshu_ort.services.answer.AnswerService;
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
public class AnswerController {
    private final AnswerService answerService;

    private final String ANSWER_PATH = "/answers";
    private final String ID_PATH = "/{id}";

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }


    @GetMapping(ANSWER_PATH)
    public List<AnswerDTO> getAllAnswers(@PathVariable Long id) {
        return answerService.getAllAnswersOfQuestion(id);
    }

    @PostMapping(ANSWER_PATH)
    public AnswerDTO saveAnswer(@RequestBody AnswerDTO dto) {
        return answerService.saveAnswer(dto);
    }

    @DeleteMapping(ANSWER_PATH + ID_PATH)
    public void deleteAnswer(@PathVariable Long id) {
        answerService.deleteAnswer(id);
    }
}
