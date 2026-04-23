package com.example.demo.service;

import com.example.demo.domain.dto.interview.EvaluateAnswersRequestDTO;
import com.example.demo.domain.dto.interview.EvaluationReportDTO;
import com.example.demo.domain.dto.interview.QuestionResponseDTO;

import java.util.List;

/**
 * Interview service interface.
 *
 * File location: src/main/java/com/example/demo/service/IInterviewService.java
 *
 * This file already exists in your project (visible in screenshot).
 * Replace its content with this complete version.
 */
public interface IInterviewService {

    /**
     * Generates personalised interview questions from a job description.
     *
     * @param jobDescription raw job description text
     * @return list of questions with cluster info and reference answers
     */
    List<QuestionResponseDTO> generateQuestions(String jobDescription);

    /**
     * Evaluates the candidate's answers and returns a per-cluster report.
     *
     * @param request answered questions with correct reference answers
     * @return evaluation report with grades per skill cluster
     */
    EvaluationReportDTO evaluateAnswers(EvaluateAnswersRequestDTO request);
}