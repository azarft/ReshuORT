package com.alatoo.reshu_ort.mappers;

import com.alatoo.reshu_ort.dto.AnswerDTO;
import com.alatoo.reshu_ort.entities.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AnswerMapper {
    @Mapping(source = "questionId", target = "question.questionId")
    Answer answerDtoToAnswer(AnswerDTO dto);

    @Mapping(source = "question.questionId", target = "questionId")
    AnswerDTO answerToAnswerDto(Answer answer);
}
