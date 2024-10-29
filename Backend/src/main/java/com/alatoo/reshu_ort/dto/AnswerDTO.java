package com.alatoo.reshu_ort.dto;

import com.alatoo.reshu_ort.entities.Question;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {

    private Long answerId;

    private Long questionId;

    private String answerText;

    private Boolean isCorrect;

}
