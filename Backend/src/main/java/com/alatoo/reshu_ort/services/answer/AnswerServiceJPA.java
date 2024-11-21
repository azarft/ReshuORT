package com.alatoo.reshu_ort.services.answer;

import com.alatoo.reshu_ort.dto.AnswerDTO;
import com.alatoo.reshu_ort.entities.Answer;
import com.alatoo.reshu_ort.exceptions.ApiException;
import com.alatoo.reshu_ort.mappers.AnswerMapper;
import com.alatoo.reshu_ort.repositories.AnswerRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerServiceJPA implements AnswerService {
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    public AnswerServiceJPA(AnswerRepository answerRepository, AnswerMapper answerMapper) {
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
    }

    @Override
    public List<AnswerDTO> getAllAnswersOfQuestion(Long id) {
        List<Answer> answers = answerRepository.getAnswersByQuestionQuestionId(id);
        return answers.stream()
                .map(answerMapper::answerToAnswerDto)
                .collect(Collectors.toList());
    }

    @Override
    public AnswerDTO saveAnswer(AnswerDTO dto) {
        Answer answer = answerMapper.answerDtoToAnswer(dto);
        Answer savedAnswer = answerRepository.save(answer);
        return answerMapper.answerToAnswerDto(savedAnswer);
    }

    @Override
    public void deleteAnswer(Long id) {
        if (!answerRepository.existsById(id)) {
            throw new ApiException("Question not found with id", HttpStatusCode.valueOf(409));
        }
        answerRepository.deleteById(id);

    }


}
