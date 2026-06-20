package com.example.demo.service.impl;

import com.example.demo.domain.dto.interview.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * McqService
 *
 * Calls the Python MCQ API (FastAPI) for two operations:
 *   1. POST /generate-questions  — returns MCQ questions with session_id
 *   2. POST /evaluate-answers    — evaluates user answers
 *
 * Set the URL in application.properties:
 *   mcq.api.base-url=https://YOUR-NGROK-URL.ngrok-free.app
 */
@Service
public class McqService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // ── PUT YOUR PYTHON MCQ API URL HERE ──────────────────────────
    // In application.properties add:
    //   mcq.api.base-url=https://xxxx-xxxx.ngrok-free.app
    @Value("${mcq.api.base-url}")
    private String mcqApiBaseUrl;

    public McqService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("ngrok-skip-browser-warning", "true")
                .build();
    }

    // ─────────────────────────────────────────────────────────────
    // 1. GENERATE MCQ QUESTIONS
    // POST /generate-questions
    // Body: { "job_description": "..." }
    // ─────────────────────────────────────────────────────────────
    public McqSessionResponseDTO generateQuestions(String jobDescription) {
        if (jobDescription == null || jobDescription.isBlank()) {
            throw new IllegalArgumentException("jobDescription must not be blank");
        }

        Map<String, String> body = Map.of("job_description", jobDescription);

        String rawJson = webClient.post()
                .uri(mcqApiBaseUrl + "/generate-questions")
                .body(Mono.just(body), Map.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return parseSessionResponse(rawJson);
    }

    // ─────────────────────────────────────────────────────────────
    // 2. EVALUATE MCQ ANSWERS
    // POST /evaluate-answers
    // Body: { "session_id": "...", "answers": [{ "question_id": "...", "user_answer": "a" }] }
    // ─────────────────────────────────────────────────────────────
    public McqEvaluationReportDTO evaluateAnswers(McqEvaluateRequestDTO request) {
        // Build Python-compatible payload
        List<Map<String, String>> answers = new ArrayList<>();
        for (McqEvaluateRequestDTO.AnswerItemDTO item : request.getAnswers()) {
            Map<String, String> map = new HashMap<>();
            map.put("question_id", item.getQuestionId());
            map.put("user_answer", item.getUserAnswer());
            answers.add(map);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("session_id", request.getSessionId());
        body.put("answers", answers);

        String rawJson = webClient.post()
                .uri(mcqApiBaseUrl + "/evaluate-answers")
                .body(Mono.just(body), Map.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return parseEvaluationReport(rawJson);
    }

    // ─────────────────────────────────────────────────────────────
    // HELPERS
    // ─────────────────────────────────────────────────────────────
    private McqSessionResponseDTO parseSessionResponse(String json) {
        McqSessionResponseDTO dto = new McqSessionResponseDTO();
        try {
            JsonNode root = objectMapper.readTree(json);
            dto.setStatus(root.path("status").asText());
            dto.setSessionId(root.path("session_id").asText());

            List<String> skills = new ArrayList<>();
            root.path("skills").forEach(s -> skills.add(s.asText()));
            dto.setSkills(skills);

            List<McqQuestionDTO> questions = new ArrayList<>();
            root.path("questions").forEach(q -> {
                McqQuestionDTO question = new McqQuestionDTO();
                question.setQuestionId(q.path("question_id").asText());
                question.setSkill(q.path("skill").asText());
                question.setQuestion(q.path("question").asText());
                question.setOptionA(q.path("option_a").asText());
                question.setOptionB(q.path("option_b").asText());
                question.setOptionC(q.path("option_c").asText());
                question.setOptionD(q.path("option_d").asText());
                questions.add(question);
            });
            dto.setQuestions(questions);

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse MCQ session response: " + e.getMessage(), e);
        }
        return dto;
    }

    private McqEvaluationReportDTO parseEvaluationReport(String json) {
        McqEvaluationReportDTO report = new McqEvaluationReportDTO();
        try {
            JsonNode root = objectMapper.readTree(json);
            report.setStatus(root.path("status").asText());
            report.setOverallScore(root.path("overall_score").asDouble());
            report.setOverallGrade(root.path("overall_grade").asText());

            // skill_summary
            Map<String, McqEvaluationReportDTO.SkillSummaryDTO> skillSummary = new HashMap<>();
            root.path("skill_summary").fields().forEachRemaining(entry -> {
                JsonNode node = entry.getValue();
                McqEvaluationReportDTO.SkillSummaryDTO s = new McqEvaluationReportDTO.SkillSummaryDTO();
                s.setSkill(node.path("skill").asText());
                s.setAvgScore(node.path("avg_score").asDouble());
                s.setGrade(node.path("grade").asText());
                s.setNumQuestions(node.path("num_questions").asInt());
                skillSummary.put(entry.getKey(), s);
            });
            report.setSkillSummary(skillSummary);

            // question_results
            List<McqEvaluationReportDTO.QuestionResultDTO> results = new ArrayList<>();
            root.path("question_results").forEach(q -> {
                McqEvaluationReportDTO.QuestionResultDTO r = new McqEvaluationReportDTO.QuestionResultDTO();
                r.setQuestionId(q.path("question_id").asText());
                r.setSkill(q.path("skill").asText());
                r.setQuestion(q.path("question").asText());
                r.setUserAnswer(q.path("user_answer").asText());
                r.setCorrectAnswer(q.path("correct_answer").asText());
                r.setGrade(q.path("grade").asText());
                r.setHybridScore(q.path("hybrid_score").asDouble());
                results.add(r);
            });
            report.setQuestionResults(results);

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse MCQ evaluation report: " + e.getMessage(), e);
        }
        return report;
    }
}