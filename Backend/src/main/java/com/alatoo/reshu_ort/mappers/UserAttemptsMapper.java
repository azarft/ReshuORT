package com.alatoo.reshu_ort.mappers;

import com.alatoo.reshu_ort.dto.UserAttemptsDTO;
import com.alatoo.reshu_ort.entities.UserAttempts;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserAttemptsMapper {
    @Mapping(source = "resultId", target = "result.resultId")
    @Mapping(source = "questionId", target = "question.questionId")
    UserAttempts userAttemptsDtoToUserAttempts(UserAttemptsDTO dto);

    @Mapping(source = "result.resultId", target = "resultId")
    @Mapping(source = "question.questionId", target = "questionId")
    UserAttemptsDTO userAttemptsToUserAttemptsDto(UserAttempts userAttempts);
}
