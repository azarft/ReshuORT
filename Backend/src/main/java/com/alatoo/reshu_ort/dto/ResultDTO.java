package com.alatoo.reshu_ort.dto;

import com.alatoo.reshu_ort.entities.Test;
import com.alatoo.reshu_ort.entities.User;
import com.alatoo.reshu_ort.entities.UserAttempts;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

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
