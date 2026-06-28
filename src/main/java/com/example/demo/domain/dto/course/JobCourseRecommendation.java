package com.example.demo.domain.dto.course;

import lombok.Data;
import java.util.List;

@Data
public class JobCourseRecommendation {

    private Long jobId;
    private String jobTitle;
    private List<String> missingSkills;
    private List<CourseDTO> recommendations;
}