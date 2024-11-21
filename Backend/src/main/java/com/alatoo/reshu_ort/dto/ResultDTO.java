package com.alatoo.reshu_ort.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {

    private Long resultId;

    private Long userId;

    private Long testId;

    private int score;

    private LocalDateTime attemptDate;

}
