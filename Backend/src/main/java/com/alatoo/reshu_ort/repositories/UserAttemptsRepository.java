package com.alatoo.reshu_ort.repositories;

import com.alatoo.reshu_ort.entities.UserAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAttemptsRepository extends JpaRepository<UserAttempt, Long> {
    List<UserAttempt> getUserAttemptsByResultResultId(Long id);
    Optional<UserAttempt> findByQuestionQuestionIdAndResultResultId(Long questionId, Long resultId);
}
