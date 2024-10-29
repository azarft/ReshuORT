package com.alatoo.reshu_ort.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAttemptsDTO {

    private Long attemptsId;

    private Long resultId;

    private Long questionId;

    private Long selectedAnswerId;

}
