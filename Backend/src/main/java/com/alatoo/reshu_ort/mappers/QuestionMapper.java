package com.alatoo.reshu_ort.mappers;

import com.alatoo.reshu_ort.dto.QuestionDTO;
import com.alatoo.reshu_ort.entities.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface QuestionMapper {
    @Mapping(source = "testId", target = "test.id")
    Question questionDtoToQuestion(QuestionDTO dto);

    @Mapping(source = "test.id", target = "testId")
    QuestionDTO questionToQuestionDto(Question question);
}
