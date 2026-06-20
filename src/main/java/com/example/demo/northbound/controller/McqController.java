package com.example.demo.northbound.controller;

import com.example.demo.domain.dto.interview.*;
import com.example.demo.service.impl.McqService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * McqController — REST endpoints for the MCQ interview module.
 *
 * Base URL: /api/interview/mcq
 *
 * POST /api/interview/mcq/generate   → generate MCQ questions from job description
 * POST /api/interview/mcq/evaluate   → evaluate user MCQ answers
 */
@RestController
@RequestMapping("/api/interview/mcq")
public class McqController {

    private final McqService mcqService;

    public McqController(McqService mcqService) {
        this.mcqService = mcqService;
    }

    @PostMapping("/generate")
    public ResponseEntity<McqSessionResponseDTO> generateQuestions(
            @RequestBody McqGenerateRequestDTO request) {
        McqSessionResponseDTO response = mcqService.generateQuestions(request.getJobDescription());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/evaluate")
    public ResponseEntity<McqEvaluationReportDTO> evaluateAnswers(
            @RequestBody McqEvaluateRequestDTO request) {
        McqEvaluationReportDTO report = mcqService.evaluateAnswers(request);
        return ResponseEntity.ok(report);
    }
}