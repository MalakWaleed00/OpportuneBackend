package com.example.demo.service;
import java.util.List;
import com.example.demo.domain.dto.job.JobDetails;
public interface ISerpApiService {
    List<JobDetails> searchJobs(String jobTitle, List<String> skills);
}

