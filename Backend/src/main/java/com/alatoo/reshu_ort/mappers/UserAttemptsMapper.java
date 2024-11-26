package com.alatoo.reshu_ort.mappers;

import com.alatoo.reshu_ort.dto.UserAttemptDTO;
import com.alatoo.reshu_ort.entities.UserAttempt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserAttemptsMapper {
    @Mapping(source = "resultId", target = "result.resultId")
    @Mapping(source = "questionId", target = "question.questionId")
    @Mapping(source = "selectedAnswerId", target = "selectedAnswer.answerId")
    UserAttempt userAttemptsDtoToUserAttempts(UserAttemptDTO dto);

    @Mapping(source = "result.resultId", target = "resultId")
    @Mapping(source = "question.questionId", target = "questionId")
    @Mapping(source = "selectedAnswer.answerId",target = "selectedAnswerId")
    UserAttemptDTO userAttemptsToUserAttemptsDto(UserAttempt userAttempt);
}
