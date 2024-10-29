package com.alatoo.reshu_ort.mappers;

import com.alatoo.reshu_ort.dto.TestDTO;
import com.alatoo.reshu_ort.entities.Test;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TestMapper {
    @Mapping(source = "createdBy", target = "createdBy.id")
    Test testDtoToTest(TestDTO dto);

    @Mapping(source = "createdBy.id", target = "createdBy")
    TestDTO testToTestDto(Test test);
}
