package com.example.demo.domain.dto.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class CourseDTO {

    private String courseName;
    private String url;
    private String matchedSkill;
    private String missingSkill;
    private Double matchScore;
    private String difficulty;
    private Double rating;
}