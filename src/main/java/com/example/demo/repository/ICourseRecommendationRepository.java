package com.example.demo.repository;

import com.example.demo.southbound.entity.CourseRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICourseRecommendationRepository extends JpaRepository<CourseRecommendation, Long> {

    List<CourseRecommendation> findByUserId(Long userId);

    List<CourseRecommendation> findByUserIdAndJobId(Long userId, Long jobId);

    void deleteByUserIdAndJobId(Long userId, Long jobId);
}