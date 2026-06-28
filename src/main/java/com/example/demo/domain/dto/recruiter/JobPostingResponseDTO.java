package com.example.demo.domain.dto.recruiter;

import com.example.demo.southbound.Enum.ExperienceLevel;
import com.example.demo.southbound.Enum.JobPostingStatus;
import com.example.demo.southbound.Enum.JobType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class JobPostingResponseDTO {
    private Long id;
    private String title;
    private String companyName;
    private String location;
    private JobType jobType;
    private ExperienceLevel experienceLevel;
    private String salaryRange;
    private String description;
    private List<String> skills;
    private LocalDate deadline;
    private JobPostingStatus status;
    private Integer applicationCount;
    private LocalDateTime postedAt;
}