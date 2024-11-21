package com.alatoo.reshu_ort.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attempts_id")
    private Long attemptsId;

    @ManyToOne
    @JoinColumn(name = "result_id")
    private Result result;

    @OneToOne
    private Question question;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "selected_answer_id")
    private Answer selectedAnswer;
}
