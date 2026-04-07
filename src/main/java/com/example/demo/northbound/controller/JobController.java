package com.example.demo.northbound.controller;

import com.example.demo.domain.dto.job.JobDetails;
import com.example.demo.service.IJobService;
import com.example.demo.service.ISerpApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final IJobService jobService;
    private final ISerpApiService serpApiService;

    @GetMapping("/{id}")
    public JobDetails getJobById(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    @GetMapping("/all")
    public List<JobDetails> getAllJobs(
            @RequestParam (required = false)  String title,
            @RequestParam(required = false) List<String> skills
    ) {
        return serpApiService.searchJobs(title, skills);
    }
}