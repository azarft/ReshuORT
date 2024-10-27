package com.alatoo.reshu_ort.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private long resultId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    private Test test;

    private int score;

    @Column(name = "attempt_date")
    private LocalDateTime attemptDate;

    @OneToMany(mappedBy = "result")
    private Set<UserAttempts> userAttempts;

    @PrePersist
    private void init(){
        this.userAttempts = new HashSet<>();
    }
}
