package com.alatoo.reshu_ort.mappers;

import com.alatoo.reshu_ort.dto.UserAttemptDTO;
import com.alatoo.reshu_ort.entities.UserAttempt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserAttemptsMapper {
    @Mapping(source = "resultId", target = "result.resultId")
    @Mapping(source = "questionId", target = "question.questionId")
    UserAttempt userAttemptsDtoToUserAttempts(UserAttemptDTO dto);

    @Mapping(source = "result.resultId", target = "resultId")
    @Mapping(source = "question.questionId", target = "questionId")
    UserAttemptDTO userAttemptsToUserAttemptsDto(UserAttempt userAttempt);
}
