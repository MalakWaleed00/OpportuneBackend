package com.example.demo.domain.dto.application;

import com.example.demo.southbound.Enum.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ApplicationResponseDTO {
    private Long id;
    private Long userId;
    private String jobTitle;
    private String company;
    private String location;
    private String salary;
    private LocalDate appliedDate;
    private ApplicationStatus status;
    private String notes;
}