package com.alatoo.reshu_ort.mappers;

import com.alatoo.reshu_ort.dto.UserAttemptsDTO;
import com.alatoo.reshu_ort.entities.UserAttempts;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserAttemptsMapper {
    @Mapping(source = "resultId", target = "result.id")
    @Mapping(source = "questionId", target = "question.id")
    UserAttempts userAttemptsDtoToUserAttempts(UserAttemptsDTO dto);

    @Mapping(source = "result.id", target = "resultId")
    @Mapping(source = "question.id", target = "questionId")
    UserAttemptsDTO userAttemptsToUserAttemptsDto(UserAttempts userAttempts);
}
