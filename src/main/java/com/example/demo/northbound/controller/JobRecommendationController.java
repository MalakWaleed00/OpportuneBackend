package com.example.demo.northbound.controller;

import com.example.demo.domain.dto.job.JobRequestDTO;
import com.example.demo.domain.dto.job.JobResponseDTO;
import com.example.demo.domain.dto.job.RecommendedJobDTO;
import com.example.demo.service.impl.JobRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobRecommendationController {

    private final JobRecommendationService recommendationService;

    @PostMapping("/recommend")
    public List<JobResponseDTO> recommendJobs(@RequestBody JobRequestDTO request) {
        return recommendationService.getRecommendations(
                request.getSkills(),
                request.getExperience(),
                request.getTopK()
        );
    }

//    @GetMapping("/recommended/{userId}")
//    public ResponseEntity<List<RecommendedJobDTO>> getRecommendedJobs(
//            @PathVariable Long userId
//    ) {
//
//        List<RecommendedJobDTO> jobs =
//                recommendationService.getRecommendations(userId, 20);
//
//        return ResponseEntity.ok(jobs);
//    }
}
