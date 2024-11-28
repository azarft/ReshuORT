package com.alatoo.reshu_ort.services.question;

import com.alatoo.reshu_ort.dto.QuestionDTO;
import com.alatoo.reshu_ort.entities.Question;
import com.alatoo.reshu_ort.entities.Test;
import com.alatoo.reshu_ort.exceptions.ApiException;
import com.alatoo.reshu_ort.mappers.QuestionMapper;
import com.alatoo.reshu_ort.repositories.QuestionRepository;
import com.alatoo.reshu_ort.repositories.TestRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionServiceJPA implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final TestRepository testRepository;

    public QuestionServiceJPA(QuestionRepository questionRepository, QuestionMapper questionMapper, TestRepository testRepository) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.testRepository = testRepository;
    }

    @Override
    public List<QuestionDTO> findAllQuestionsOfTest(Long id) {
        List<Question> questions = questionRepository.getQuestionsByTestTestId(id);
        return questions.stream()
                .map(questionMapper::questionToQuestionDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuestionDTO saveQuestion(QuestionDTO dto) {
        Question question = questionMapper.questionDtoToQuestion(dto);
        Question savedQuestion = questionRepository.save(question);
        return questionMapper.questionToQuestionDto(savedQuestion);
    }

    @Override
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new ApiException("Question not found with id", HttpStatusCode.valueOf(409));
        }
        questionRepository.deleteById(id);
    }

    @Override
    public QuestionDTO findQuestionBy(Long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        Question question = optionalQuestion.orElseThrow(() -> new ApiException("Question not found with id", HttpStatusCode.valueOf(409)));
        return questionMapper.questionToQuestionDto(question);
    }

    @Override
    public List<QuestionDTO> findAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream()
                .map(questionMapper::questionToQuestionDto)
                .collect(Collectors.toList());
    }
}
