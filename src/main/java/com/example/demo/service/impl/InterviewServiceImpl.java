package com.example.demo.service.impl;

import com.example.demo.domain.dto.interview.EvaluateAnswersRequestDTO;
import com.example.demo.domain.dto.interview.EvaluationReportDTO;
import com.example.demo.domain.dto.interview.QuestionResponseDTO;
import com.example.demo.service.IInterviewService;
import com.example.demo.service.ai.impl.RagQuestionService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * InterviewServiceImpl
 *
 * File location: src/main/java/com/example/demo/service/impl/InterviewServiceImpl.java
 *
 * This file already exists in your project (visible in screenshot).
 * Replace its content with this complete version.
 */
@Service
public class InterviewServiceImpl implements IInterviewService {

    private final RagQuestionService ragQuestionService;
    private final ObjectMapper       objectMapper = new ObjectMapper();

    public InterviewServiceImpl(RagQuestionService ragQuestionService) {
        this.ragQuestionService = ragQuestionService;
    }

    // ─────────────────────────────────────────────────────────────
    // 1. GENERATE QUESTIONS
    // ─────────────────────────────────────────────────────────────
    @Override
    public List<QuestionResponseDTO> generateQuestions(String jobDescription) {
        return ragQuestionService.generateQuestions(jobDescription);
    }

    // ─────────────────────────────────────────────────────────────
    // 2. EVALUATE ANSWERS
    // ─────────────────────────────────────────────────────────────
    @Override
    public EvaluationReportDTO evaluateAnswers(EvaluateAnswersRequestDTO request) {
        // Build the list of answer maps expected by the Python API
        List<Map<String, Object>> answers = new ArrayList<>();
        for (EvaluateAnswersRequestDTO.AnswerItemDTO item : request.getAnswers()) {
            Map<String, Object> map = new HashMap<>();
            map.put("cluster_id",     item.getClusterId());
            map.put("skills",         item.getSkills());
            map.put("question",       item.getQuestion());
            map.put("user_answer",    item.getUserAnswer());
            map.put("correct_answer", item.getCorrectAnswer());
            answers.add(map);
        }

        // Call Python API
        String rawJson = ragQuestionService.evaluateAnswers(answers);

        // Parse into EvaluationReportDTO
        return parseEvaluationReport(rawJson);
    }

    // ─────────────────────────────────────────────────────────────
    // HELPER — map Python JSON response → EvaluationReportDTO
    // ─────────────────────────────────────────────────────────────
    private EvaluationReportDTO parseEvaluationReport(String json) {
        EvaluationReportDTO report = new EvaluationReportDTO();
        try {
            JsonNode root = objectMapper.readTree(json);

            report.setStatus(root.path("status").asText());
            report.setOverallScore(root.path("overall_score").asDouble());
            report.setOverallGrade(root.path("overall_grade").asText());

            Map<String, EvaluationReportDTO.ClusterResultDTO> clusters = new HashMap<>();
            JsonNode clustersNode = root.path("clusters");
            clustersNode.fields().forEachRemaining(entry -> {
                JsonNode              node   = entry.getValue();
                EvaluationReportDTO.ClusterResultDTO cluster =
                        new EvaluationReportDTO.ClusterResultDTO();

                cluster.setClusterId(node.path("cluster_id").asInt());
                cluster.setAvgScore(node.path("avg_score").asDouble());
                cluster.setGrade(node.path("grade").asText());

                List<String> skills = new ArrayList<>();
                node.path("skills").forEach(s -> skills.add(s.asText()));
                cluster.setSkills(skills);

                List<String> weakMetrics = new ArrayList<>();
                node.path("weak_metrics").forEach(w -> weakMetrics.add(w.asText()));
                cluster.setWeakMetrics(weakMetrics);

                clusters.put(entry.getKey(), cluster);
            });

            report.setClusters(clusters);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse evaluation report: " + e.getMessage(), e);
        }
        return report;
    }
}