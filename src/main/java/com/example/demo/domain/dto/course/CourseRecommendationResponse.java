package com.example.demo.domain.dto.course;

import lombok.Data;
import java.util.List;

@Data
public class CourseRecommendationResponse {

    private List<JobCourseRecommendation> jobRecommendations;
}