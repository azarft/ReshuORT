package com.alatoo.reshu_ort.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.TrueFalseConverter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answerId;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "answer_text")
    private String answerText;

    @Column(name = "is_correct", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean isCorrect;
}
