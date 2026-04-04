package com.example.demo.service.ai.impl;

import com.example.demo.service.ai.JobRecommendationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiJobRecommendationModel implements JobRecommendationModel {

    private final RestTemplate restTemplate;

    @Override
    public List recommendJobs(Long userId, int limit) {

        String url = "http://ai-model/recommend";

        Map<String, Object> request = Map.of(
                "userId", userId,
                "limit", limit
        );

        ResponseEntity<List> response =
                restTemplate.postForEntity(url, request, List.class);

        return response.getBody();
    }
}
