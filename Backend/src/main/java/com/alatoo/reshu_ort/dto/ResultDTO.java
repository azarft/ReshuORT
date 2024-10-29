package com.alatoo.reshu_ort.dto;

import com.alatoo.reshu_ort.entities.Test;
import com.alatoo.reshu_ort.entities.User;
import com.alatoo.reshu_ort.entities.UserAttempts;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {

    private long resultId;

    private long userId;

    private long testId;

    private int score;

    private LocalDateTime attemptDate;

}
