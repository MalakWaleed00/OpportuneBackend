package com.example.demo.domain.dto.parsing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CvDTO {

    private String name;
    private String email;
    private String phone;
    @JsonProperty("experience_level")
    private String experienceLevel;

    private List<String> skills;
    private List<EducationDTO> education;
    private List<JobDTO> jobs;
    private List<ProjectDTO> projects;
    private List<InternshipDTO> internships;
}
