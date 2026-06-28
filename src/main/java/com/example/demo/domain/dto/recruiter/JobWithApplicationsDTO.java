package com.example.demo.domain.dto.recruiter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobWithApplicationsDTO {
    private Long jobId;
    private String title;
    private Integer applicationCount;
}