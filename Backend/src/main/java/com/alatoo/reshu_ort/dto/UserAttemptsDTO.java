package com.alatoo.reshu_ort.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAttemptsDTO {

    private long attemptsId;

    private long resultId;

    private long questionId;

    private long selectedAnswerId;

}
