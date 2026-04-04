package com.example.demo.repository;

import com.example.demo.southbound.entity.JobRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IJobRecommendationRepository
        extends JpaRepository<JobRecommendation, Long> {

    List<JobRecommendation> findByUserId(Long userId);
}
