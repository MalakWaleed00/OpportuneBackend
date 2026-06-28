package com.example.demo.domain.dto.application;

import com.example.demo.southbound.Enum.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ApplicationSummaryDTO {
    private Long id;
    private String jobTitle;
    private String company;
    private ApplicationStatus status;
    private LocalDate appliedDate;
}