package com.example.demo.service;

import com.example.demo.domain.dto.job.JobDetails;
import com.example.demo.southbound.entity.Job;

public interface IJobService {
    JobDetails getJobById(Long id);
}
