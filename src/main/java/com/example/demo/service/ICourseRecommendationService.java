package com.example.demo.service;

import com.example.demo.domain.dto.course.CourseDTO;
import com.example.demo.domain.dto.course.CourseRecommendationRequest;

import java.util.List;

public interface ICourseRecommendationService {

    List<CourseDTO> recommend(CourseRecommendationRequest request);
}