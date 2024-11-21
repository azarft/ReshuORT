package com.alatoo.reshu_ort.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private Long resultId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @Column(nullable = false)
    private Test test;

    @Column(nullable = false)
    private int score;

    @Column(name = "attempt_date")
    private LocalDateTime attemptDate;

    @OneToMany(mappedBy = "result")
    private Set<UserAttempt> userAttempts;

    @PrePersist
    private void init(){
        this.userAttempts = new HashSet<>();
    }
}
