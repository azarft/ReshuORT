package com.alatoo.reshu_ort.dto;


import com.alatoo.reshu_ort.entities.Answer;
import com.alatoo.reshu_ort.entities.Test;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    private long questionId;

    private long testId;

    private String questionText;

}
