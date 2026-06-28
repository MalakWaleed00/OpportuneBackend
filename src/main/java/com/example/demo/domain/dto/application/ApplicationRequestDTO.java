package com.example.demo.domain.dto.application;

import com.example.demo.southbound.Enum.ApplicationStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ApplicationRequestDTO {
    private String jobTitle;
    private String company;
    private String location;
    private String salary;
    private LocalDate appliedDate;
    private ApplicationStatus status;
    private String notes;
}