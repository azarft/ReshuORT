package com.alatoo.reshu_ort.dto;

import com.alatoo.reshu_ort.entities.Answer;
import com.alatoo.reshu_ort.entities.Question;
import com.alatoo.reshu_ort.entities.Result;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAttemtsDTO {

    private long attemptsId;

    private long resultId;

    private long questionId;

    private long selectedAnswerId;

}
