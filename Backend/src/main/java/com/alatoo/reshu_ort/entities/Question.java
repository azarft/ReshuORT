package com.alatoo.reshu_ort.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @Column(name = "question_text")
    private String questionText;

    @OneToMany(mappedBy = "question")
    private Set<Answer> answers;

    @PrePersist
    private void init(){
        this.answers = new HashSet<>();
    }
}
