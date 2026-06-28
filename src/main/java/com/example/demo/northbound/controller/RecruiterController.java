package com.example.demo.northbound.controller;

import com.example.demo.domain.dto.recruiter.JobPostingRequestDTO;
import com.example.demo.domain.dto.recruiter.JobPostingResponseDTO;
import com.example.demo.domain.dto.recruiter.RecruiterDashboardDTO;
import com.example.demo.service.IJobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiter")
@RequiredArgsConstructor
public class RecruiterController {

    private final IJobPostingService jobPostingService;

    @PostMapping("/jobs")
    public ResponseEntity<JobPostingResponseDTO> createJob(@RequestBody JobPostingRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobPostingService.createJob(request));
    }

    @GetMapping("/jobs/my")
    public ResponseEntity<List<JobPostingResponseDTO>> getMyJobs() {
        return ResponseEntity.ok(jobPostingService.getMyJobs());
    }

    @PutMapping("/jobs/{id}")
    public ResponseEntity<JobPostingResponseDTO> updateJob(
            @PathVariable Long id,
            @RequestBody JobPostingRequestDTO request) {
        return ResponseEntity.ok(jobPostingService.updateJob(id, request));
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobPostingService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/jobs/{id}/toggle-status")
    public ResponseEntity<JobPostingResponseDTO> toggleStatus(@PathVariable Long id) {
        return ResponseEntity.ok(jobPostingService.toggleStatus(id));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<RecruiterDashboardDTO> getDashboard() {
        return ResponseEntity.ok(jobPostingService.getDashboard());
    }
}