package com.example.demo.service.impl;

import com.example.demo.domain.dto.course.CourseDTO;
import com.example.demo.domain.dto.course.CourseRecommendationRequest;
import com.example.demo.service.ICourseRecommendationService;
import com.example.demo.service.ai.CourseRecommendationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseRecommendationServiceImpl implements ICourseRecommendationService {

    private final CourseRecommendationModel courseRecommendationModel;

    @Override
    public List<CourseDTO> recommend(CourseRecommendationRequest request) {
        return courseRecommendationModel.recommendCourses(request);
    }
}