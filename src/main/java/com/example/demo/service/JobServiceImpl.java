package com.example.demo.service;

import com.example.demo.domain.dto.job.JobDetails;
import com.example.demo.repository.IJobRepository;
import com.example.demo.southbound.entity.Job;
import com.example.demo.southbound.mapper.JobMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements IJobService {

    private final IJobRepository jobRepository;
    private final JobMapper jobMapper;

    @Override
    public JobDetails getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        return jobMapper.toDTO(job);
    }
}