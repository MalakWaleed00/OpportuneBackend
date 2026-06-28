package com.example.demo.domain.dto.course;

import lombok.Data;

@Data
public class CourseRecommendationControllerRequest {

    private Long userId;
    private int topK;
}