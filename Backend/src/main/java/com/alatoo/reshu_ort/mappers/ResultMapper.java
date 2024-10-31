package com.alatoo.reshu_ort.mappers;

import com.alatoo.reshu_ort.dto.ResultDTO;
import com.alatoo.reshu_ort.entities.Result;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ResultMapper {
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "testId", target = "test.testId")
    Result resultDtoToResult(ResultDTO dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "test.testId", target = "testId")
    ResultDTO resultToResultDto(Result result);
}
