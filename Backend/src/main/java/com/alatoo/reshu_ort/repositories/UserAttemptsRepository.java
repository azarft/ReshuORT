package com.alatoo.reshu_ort.repositories;

import com.alatoo.reshu_ort.entities.UserAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAttemptsRepository extends JpaRepository<UserAttempt, Long> {
    List<UserAttempt> getUserAttemptsByResultResultId(Long id);
}
