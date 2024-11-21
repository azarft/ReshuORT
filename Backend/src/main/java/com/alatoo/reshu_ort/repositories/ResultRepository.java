package com.alatoo.reshu_ort.repositories;

import com.alatoo.reshu_ort.entities.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> getResultsByUserId(Long id);
    boolean existsByResultIdAndUserId(Long id, Long userId);
    Optional<Result> findByResultIdAndUserId(Long id, Long userId);
}
