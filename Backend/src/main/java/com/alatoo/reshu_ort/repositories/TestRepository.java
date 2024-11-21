package com.alatoo.reshu_ort.repositories;

import com.alatoo.reshu_ort.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    boolean existsByTestIdAndCreatedById(Long id, Long userId);
    List<Test> findByCreatedById(Long id);
}
