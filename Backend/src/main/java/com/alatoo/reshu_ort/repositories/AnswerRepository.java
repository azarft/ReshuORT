package com.alatoo.reshu_ort.repositories;

import com.alatoo.reshu_ort.entities.Answer;
import com.alatoo.reshu_ort.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> getAnswersByQuestionQuestionId(Long id);
    Answer findFirstByQuestion(Question question);

}
