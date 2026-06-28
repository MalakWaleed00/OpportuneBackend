package com.example.demo.service;

import com.example.demo.domain.dto.course.CourseRecommendationControllerRequest;
import com.example.demo.domain.dto.course.CourseRecommendationResponse;

public interface ICourseRecommendationService {

    CourseRecommendationResponse recommend(CourseRecommendationControllerRequest request);
}