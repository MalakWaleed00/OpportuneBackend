package com.example.demo.service.ai;
import com.example.demo.domain.dto.job.JobResponseDTO;
import java.util.List;


public interface JobRecommendationModel {

    List<JobResponseDTO> recommendJobs(List<String> userSkills, String userExperience, int topk);
}
