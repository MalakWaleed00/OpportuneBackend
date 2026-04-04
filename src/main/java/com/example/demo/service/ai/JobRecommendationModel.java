package com.example.demo.service.ai;

import java.util.List;

public interface JobRecommendationModel {

    List<Long> recommendJobs(Long userId, int limit);
}
