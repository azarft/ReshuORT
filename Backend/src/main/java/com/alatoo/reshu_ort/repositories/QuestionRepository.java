package com.alatoo.reshu_ort.repositories;

import com.alatoo.reshu_ort.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> getQuestionsByTestTestId(Long id);

}
