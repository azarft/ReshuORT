package com.alatoo.reshu_ort.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private Long testId;

    @Column(name = "test_name")
    private String testName;

    private String subject;

    @Column(name = "time_limit")
    private Long timeLimit;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Column(nullable = false)
    private User createdBy;

    @OneToMany(mappedBy = "test")
    private Set<Question> questions;

    @PrePersist
    private void init(){
        this.questions = new HashSet<>();
    }
}
