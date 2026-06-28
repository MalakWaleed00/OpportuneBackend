package com.example.demo.domain.dto.recruiter;

import com.example.demo.southbound.Enum.ExperienceLevel;
import com.example.demo.southbound.Enum.JobType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class JobPostingRequestDTO {
    private String title;
    private String companyName;
    private String location;
    private JobType jobType;
    private ExperienceLevel experienceLevel;
    private String salaryRange;
    private String description;
    private List<String> skills;
    private LocalDate deadline;
}