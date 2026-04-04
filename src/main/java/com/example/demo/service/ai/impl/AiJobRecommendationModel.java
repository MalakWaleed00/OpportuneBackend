package com.example.demo.service.ai.impl;

import com.example.demo.service.ai.JobRecommendationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiJobRecommendationModel implements JobRecommendationModel {

    private final RestTemplate restTemplate;

    @Override
    public List<String> recommendJobs(List<String> userSkills, String userExperience, int topK) {
        // MOCK: return dummy job IDs for testing
        List<String> jobs = new ArrayList<>();
        jobs.add("Backend");
        return jobs;
    }
}
