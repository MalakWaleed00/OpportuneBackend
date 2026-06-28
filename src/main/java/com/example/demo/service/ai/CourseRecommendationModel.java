package com.example.demo.service.ai;

import com.example.demo.domain.dto.course.CourseDTO;
import com.example.demo.domain.dto.course.CourseRecommendationRequest;
import java.util.List;

public interface CourseRecommendationModel {

    List<CourseDTO> recommendCourses(CourseRecommendationRequest request);
}