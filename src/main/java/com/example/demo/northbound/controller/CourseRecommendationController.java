package com.example.demo.northbound.controller;

import com.example.demo.domain.dto.course.CourseRecommendationControllerRequest;
import com.example.demo.domain.dto.course.CourseRecommendationResponse;
import com.example.demo.service.ICourseRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseRecommendationController {

    private final ICourseRecommendationService courseRecommendationService;

    @PostMapping("/recommend")
    public ResponseEntity<CourseRecommendationResponse> recommend(
            @RequestBody CourseRecommendationControllerRequest request) {

        CourseRecommendationResponse response = courseRecommendationService.recommend(request);
        return ResponseEntity.ok(response);
    }
}