package com.example.demo.service.ai.impl;

import com.example.demo.domain.dto.interview.GenerateQuestionsRequestDTO;
import com.example.demo.domain.dto.interview.QuestionResponseDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.example.demo.domain.dto.interview.QuestionResponseDTO;
import com.example.demo.service.ai.QuestionGenerationService;
import java.util.List;
import com.example.demo.southbound.Enum.QuestionFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.example.demo.service.ai.QuestionGenerationService;
/**
 * RagQuestionService
 *
 * Calls the Python RAG API (FastAPI + ngrok) for two operations:
 *   1. POST /interview/generate  — returns personalised interview questions
 *   2. POST /interview/evaluate  — returns per-cluster feedback report
 *
 * File location: src/main/java/com/example/demo/service/ai/impl/RagQuestionService.java
 */
@Service
public class RagQuestionService implements QuestionGenerationService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Set the Python API base URL in application.properties:
     *   rag.api.base-url=https://YOUR-NGROK-URL.ngrok-free.app
     */
    @Value("${rag.api.base-url}")
    private String ragApiBaseUrl;

    public RagQuestionService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .defaultHeader("Content-Type","application/json")
                .defaultHeader("ngrok-skip-browser-warning", "true")
                .build();
    }

    // ─────────────────────────────────────────────────────────────
    // 1. GENERATE QUESTIONS
    // POST /interview/generate
    // Body : { "job_description": "..." }
    // Returns list of QuestionResponseDTO
    // ─────────────────────────────────────────────────────────────
    public List<QuestionResponseDTO> generateQuestions(String jobDescription) {
        if (jobDescription == null || jobDescription.isBlank()) {
            throw new IllegalArgumentException("jobDescription must not be null or blank");
        }
        Map<String, String> requestBody = Map.of("job_description", jobDescription);

        String responseJson = webClient.post()
                .uri(ragApiBaseUrl + "/interview/generate")
                .header("ngrok-skip-browser-warning", "true")
                .body(Mono.just(requestBody), Map.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return parseQuestionsFromResponse(responseJson);
    }

    // ─────────────────────────────────────────────────────────────
    // 2. EVALUATE ANSWERS
    // POST /interview/evaluate
    // Body : { "answers": [ { "cluster_id": 0, "skills": [...],
    //           "question": "...", "user_answer": "...",
    //           "correct_answer": "..." } ] }
    // Returns the raw JSON string from Python (your InterviewServiceImpl
    // can parse this into whatever response model you need)
    // ─────────────────────────────────────────────────────────────
    public String evaluateAnswers(List<Map<String, Object>> answers) {
        Map<String, Object> requestBody = Map.of("answers", answers);

        return webClient.post()
                .uri(ragApiBaseUrl + "/interview/evaluate")
                .body(Mono.just(requestBody), Map.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // ─────────────────────────────────────────────────────────────
    // HELPER — parse Python response JSON into QuestionResponseDTO list
    // ─────────────────────────────────────────────────────────────
    private List<QuestionResponseDTO> parseQuestionsFromResponse(String json) {
        List<QuestionResponseDTO> result = new ArrayList<>();
        try {
            JsonNode root      = objectMapper.readTree(json);
            JsonNode questions = root.path("questions");

            if (questions.isArray()) {
                for (JsonNode q : questions) {
                    QuestionResponseDTO dto = new QuestionResponseDTO();
                    dto.setClusterId(q.path("cluster_id").asInt());
                    dto.setQuestion(q.path("question").asText());
                    dto.setAnswer(q.path("answer").asText());

                    List<String> skills = new ArrayList<>();
                    q.path("skills").forEach(s -> skills.add(s.asText()));
                    dto.setSkills(skills);

                    result.add(dto);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse RAG API response: " + e.getMessage(), e);
        }
        return result;
    }
    @Override
    public List<String> generateQuestions(String topic, QuestionFormat format, int numberOfQuestions) {
        return generateQuestions(topic)
                .stream()
                .map(QuestionResponseDTO::getQuestion)
                .collect(java.util.stream.Collectors.toList());
    }
}