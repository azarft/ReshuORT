package com.alatoo.reshu_ort.dto;


import com.alatoo.reshu_ort.entities.Answer;
import com.alatoo.reshu_ort.entities.Test;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    private Long questionId;

    private Long testId;

    private String questionText;

}
