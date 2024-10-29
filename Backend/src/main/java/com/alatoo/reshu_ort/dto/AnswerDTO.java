package com.alatoo.reshu_ort.dto;

import com.alatoo.reshu_ort.entities.Question;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {

    private long answerId;

    private long questionId;

    private String answerText;

    private Boolean isCorrect;

}
