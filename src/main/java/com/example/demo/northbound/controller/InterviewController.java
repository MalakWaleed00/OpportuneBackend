package com.example.demo.northbound.controller;

import com.example.demo.domain.dto.interview.EvaluateAnswersRequestDTO;
import com.example.demo.domain.dto.interview.EvaluationReportDTO;
import com.example.demo.domain.dto.interview.GenerateQuestionsRequestDTO;
import com.example.demo.domain.dto.interview.QuestionResponseDTO;
import com.example.demo.service.IInterviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * InterviewController — REST endpoints for the interview module.
 *
 * File location: src/main/java/com/example/demo/northbound/controller/InterviewController.java
 *
 * This file already exists in your project (visible in screenshot).
 * Replace its content with this complete version.
 *
 * Base URL  : /api/interview
 *
 * Endpoints :
 *   POST /api/interview/generate  → generate questions from job description
 *   POST /api/interview/evaluate  → evaluate candidate's answers
 */
@RestController
@RequestMapping("/api/interview")
public class InterviewController {

    private final IInterviewService interviewService;

    public InterviewController(IInterviewService interviewService) {
        this.interviewService = interviewService;
    }

    // ─────────────────────────────────────────────────────────────
    // ENDPOINT 1 — Generate Questions
    // POST /api/interview/generate
    //
    // Request body:
    // {
    //   "jobDescription": "We are looking for a DevOps engineer..."
    // }
    //
    // Response body:
    // [
    //   {
    //     "clusterId": 0,
    //     "skills": ["docker", "ecs"],
    //     "question": "How do you create a Docker image?",
    //     "answer": "ideal answer..."
    //   },
    //   ...
    // ]
    // ─────────────────────────────────────────────────────────────
    @PostMapping("/generate")
    public ResponseEntity<List<QuestionResponseDTO>> generateQuestions(
            @RequestBody GenerateQuestionsRequestDTO request) {

        List<QuestionResponseDTO> questions =
                interviewService.generateQuestions(request.getJobDescription());

        return ResponseEntity.ok(questions);
    }

    // ─────────────────────────────────────────────────────────────
    // ENDPOINT 2 — Evaluate Answers
    // POST /api/interview/evaluate
    //
    // Request body:
    // {
    //   "answers": [
    //     {
    //       "clusterId": 0,
    //       "skills": ["docker", "ecs"],
    //       "question": "How do you create a Docker image?",
    //       "userAnswer": "candidate's spoken/typed answer",
    //       "correctAnswer": "ideal answer from generate step"
    //     }
    //   ]
    // }
    //
    // Response body:
    // {
    //   "status": "report_ready",
    //   "overallScore": 0.72,
    //   "overallGrade": "Good 👍",
    //   "clusters": {
    //     "0": {
    //       "clusterId": 0,
    //       "skills": ["docker", "ecs"],
    //       "avgScore": 0.75,
    //       "grade": "Excellent ✅",
    //       "weakMetrics": []
    //     }
    //   }
    // }
    // ─────────────────────────────────────────────────────────────
    @PostMapping("/evaluate")
    public ResponseEntity<EvaluationReportDTO> evaluateAnswers(
            @RequestBody EvaluateAnswersRequestDTO request) {

        EvaluationReportDTO report = interviewService.evaluateAnswers(request);
        return ResponseEntity.ok(report);
    }
}