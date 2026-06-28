package com.example.demo.domain.dto.recruiter;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecruiterDashboardDTO {
    private int totalJobs;
    private int activeJobs;
    private int closedJobs;
    private int totalApplications;
    private List<JobWithApplicationsDTO> jobsWithApplications;
    private List<RecentJobDTO> recentJobs;
}