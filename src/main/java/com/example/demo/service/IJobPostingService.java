package com.example.demo.service;

import com.example.demo.domain.dto.recruiter.JobPostingRequestDTO;
import com.example.demo.domain.dto.recruiter.JobPostingResponseDTO;
import com.example.demo.domain.dto.recruiter.RecruiterDashboardDTO;

import java.util.List;

public interface IJobPostingService {

    JobPostingResponseDTO createJob(JobPostingRequestDTO request);

    List<JobPostingResponseDTO> getMyJobs();

    JobPostingResponseDTO updateJob(Long id, JobPostingRequestDTO request);

    void deleteJob(Long id);

    JobPostingResponseDTO toggleStatus(Long id);

    RecruiterDashboardDTO getDashboard();
}