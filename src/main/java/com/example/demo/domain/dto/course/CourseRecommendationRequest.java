package com.example.demo.domain.dto.course;

import lombok.Data;
import java.util.List;

@Data
public class CourseRecommendationRequest {

    private List<String> missingSkills;
    private String experienceLevel;
    private int topK;
}