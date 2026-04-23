package com.example.demo.domain.dto.interview;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
/**
 * DTO for requesting interview question generation.
 *
 * File location: src/main/java/com/example/demo/domain/dto/interview/GenerateQuestionsRequestDTO.java
 *
 * This file already exists in your project (visible in screenshot).
 * Replace its content with this if it does not already have jobDescription.
 */
@Getter
@Setter
public class GenerateQuestionsRequestDTO {
    /** The full job description text sent from the frontend. */
    @JsonProperty("jobDescription")   // ← add this
    private String jobDescription;
}