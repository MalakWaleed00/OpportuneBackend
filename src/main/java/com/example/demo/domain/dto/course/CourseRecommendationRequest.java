package com.example.demo.domain.dto.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "Direct course recommendation request")
public class CourseRecommendationRequest {

    @Schema(description = "Skills the user is missing", example = "[\"Python\", \"Docker\"]", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("missing_skills")
    private List<String> missingSkills;

    @Schema(description = "User experience level", example = "beginner", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("experience_level")
    private String experienceLevel;

    @Schema(description = "Number of courses to return", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("topK")
    private int topK;
}