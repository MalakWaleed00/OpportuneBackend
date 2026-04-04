package com.example.demo.service.ai;

import java.util.List;

public interface JobRecommendationModel {

    List<String> recommendJobs(List<String> userSkills, String userExperience, int topk);
}
