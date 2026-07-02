package com.example.demo.northbound.controller;

import com.example.demo.domain.dto.course.CourseDTO;
import com.example.demo.domain.dto.course.CourseRecommendationRequest;
import com.example.demo.service.ICourseRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseRecommendationController {

    private final ICourseRecommendationService courseRecommendationService;

    @PostMapping("/recommend")
    public ResponseEntity<List<CourseDTO>> recommend(
            @RequestBody CourseRecommendationRequest request) {

        return ResponseEntity.ok(courseRecommendationService.recommend(request));
    }
}