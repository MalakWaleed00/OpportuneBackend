package com.example.demo.service.impl;

import com.example.demo.common.util.IAuthUtils;
import com.example.demo.domain.dto.recruiter.*;
import com.example.demo.repository.IJobPostingRepository;
import com.example.demo.service.IJobPostingService;
import com.example.demo.southbound.Enum.JobPostingStatus;
import com.example.demo.southbound.entity.JobPosting;
import com.example.demo.southbound.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class JobPostingServiceImpl implements IJobPostingService {

    private final IJobPostingRepository jobPostingRepository;
    private final IAuthUtils authUtils;

    @Override
    public JobPostingResponseDTO createJob(JobPostingRequestDTO request) {
        User recruiter = authUtils.getCurrentUser();

        JobPosting posting = JobPosting.builder()
                .recruiter(recruiter)
                .title(request.getTitle())
                .companyName(request.getCompanyName())
                .location(request.getLocation())
                .jobType(request.getJobType())
                .experienceLevel(request.getExperienceLevel())
                .salaryRange(request.getSalaryRange())
                .description(request.getDescription())
                .skills(request.getSkills() != null ? request.getSkills() : new ArrayList<>())
                .deadline(request.getDeadline())
                .status(JobPostingStatus.ACTIVE)
                .applicationCount(0)
                .postedAt(LocalDateTime.now())
                .build();

        return toResponse(jobPostingRepository.save(posting));
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobPostingResponseDTO> getMyJobs() {
        Long recruiterId = authUtils.getCurrentUserId();
        return jobPostingRepository.findByRecruiterIdOrderByPostedAtDesc(recruiterId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public JobPostingResponseDTO updateJob(Long id, JobPostingRequestDTO request) {
        JobPosting posting = findAndVerifyOwner(id);

        if (request.getTitle() != null) posting.setTitle(request.getTitle());
        if (request.getCompanyName() != null) posting.setCompanyName(request.getCompanyName());
        if (request.getLocation() != null) posting.setLocation(request.getLocation());
        if (request.getJobType() != null) posting.setJobType(request.getJobType());
        if (request.getExperienceLevel() != null) posting.setExperienceLevel(request.getExperienceLevel());
        if (request.getSalaryRange() != null) posting.setSalaryRange(request.getSalaryRange());
        if (request.getDescription() != null) posting.setDescription(request.getDescription());
        if (request.getSkills() != null) posting.setSkills(request.getSkills());
        if (request.getDeadline() != null) posting.setDeadline(request.getDeadline());

        return toResponse(jobPostingRepository.save(posting));
    }

    @Override
    public void deleteJob(Long id) {
        JobPosting posting = findAndVerifyOwner(id);
        jobPostingRepository.delete(posting);
    }

    @Override
    public JobPostingResponseDTO toggleStatus(Long id) {
        JobPosting posting = findAndVerifyOwner(id);
        posting.setStatus(posting.getStatus() == JobPostingStatus.ACTIVE
                ? JobPostingStatus.CLOSED
                : JobPostingStatus.ACTIVE);
        return toResponse(jobPostingRepository.save(posting));
    }

    @Override
    @Transactional(readOnly = true)
    public RecruiterDashboardDTO getDashboard() {
        Long recruiterId = authUtils.getCurrentUserId();
        List<JobPosting> jobs = jobPostingRepository.findByRecruiterIdOrderByPostedAtDesc(recruiterId);

        int totalJobs = jobs.size();
        int activeJobs = (int) jobs.stream().filter(j -> j.getStatus() == JobPostingStatus.ACTIVE).count();
        int closedJobs = totalJobs - activeJobs;
        int totalApplications = jobs.stream().mapToInt(JobPosting::getApplicationCount).sum();

        List<JobWithApplicationsDTO> jobsWithApplications = jobs.stream()
                .filter(j -> j.getApplicationCount() > 0)
                .sorted(Comparator.comparingInt(JobPosting::getApplicationCount).reversed())
                .map(j -> JobWithApplicationsDTO.builder()
                        .jobId(j.getId())
                        .title(j.getTitle())
                        .applicationCount(j.getApplicationCount())
                        .build())
                .collect(Collectors.toList());

        List<RecentJobDTO> recentJobs = jobs.stream()
                .limit(5)
                .map(j -> RecentJobDTO.builder()
                        .id(j.getId())
                        .title(j.getTitle())
                        .location(j.getLocation())
                        .status(j.getStatus())
                        .applicationCount(j.getApplicationCount())
                        .postedAt(j.getPostedAt())
                        .build())
                .collect(Collectors.toList());

        return RecruiterDashboardDTO.builder()
                .totalJobs(totalJobs)
                .activeJobs(activeJobs)
                .closedJobs(closedJobs)
                .totalApplications(totalApplications)
                .jobsWithApplications(jobsWithApplications)
                .recentJobs(recentJobs)
                .build();
    }

    private JobPosting findAndVerifyOwner(Long id) {
        JobPosting posting = jobPostingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job posting not found"));

        Long currentUserId = authUtils.getCurrentUserId();
        if (!posting.getRecruiter().getId().equals(currentUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to modify this job posting");
        }

        return posting;
    }

    private JobPostingResponseDTO toResponse(JobPosting posting) {
        return JobPostingResponseDTO.builder()
                .id(posting.getId())
                .title(posting.getTitle())
                .companyName(posting.getCompanyName())
                .location(posting.getLocation())
                .jobType(posting.getJobType())
                .experienceLevel(posting.getExperienceLevel())
                .salaryRange(posting.getSalaryRange())
                .description(posting.getDescription())
                .skills(posting.getSkills())
                .deadline(posting.getDeadline())
                .status(posting.getStatus())
                .applicationCount(posting.getApplicationCount())
                .postedAt(posting.getPostedAt())
                .build();
    }
}