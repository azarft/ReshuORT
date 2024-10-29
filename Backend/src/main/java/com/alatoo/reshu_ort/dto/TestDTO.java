package com.alatoo.reshu_ort.dto;


import com.alatoo.reshu_ort.entities.Question;
import com.alatoo.reshu_ort.entities.User;
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
public class TestDTO {

    private long testId;

    private String testName;

    private String subject;

    private long timeLimit;

    private long createdBy;

}
