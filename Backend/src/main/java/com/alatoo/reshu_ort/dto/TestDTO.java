package com.alatoo.reshu_ort.dto;


import com.alatoo.reshu_ort.entities.Question;
import com.alatoo.reshu_ort.entities.User;
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
public class TestDTO {

    private Long testId;

    private String testName;

    private String subject;

    private Long timeLimit;

    private Long createdBy;

}
