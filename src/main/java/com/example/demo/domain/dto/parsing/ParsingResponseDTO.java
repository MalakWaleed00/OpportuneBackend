package com.example.demo.domain.dto.parsing;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ParsingResponseDTO {

    private List<String> extractedSkills;
    private String experienceLevel;
    private double matchScore;
}
