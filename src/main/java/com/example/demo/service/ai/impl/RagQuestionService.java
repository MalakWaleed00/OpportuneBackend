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

@Service
public class RagQuestionService implements QuestionGenerationService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Value("${rag.api.base-url}")
    private String ragApiBaseUrl;

    public RagQuestionService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .defaultHeader("Content-Type","application/json")
                .defaultHeader("ngrok-skip-browser-warning", "true")
                .build();
    }

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

    public String evaluateAnswers(List<Map<String, Object>> answers) {
        Map<String, Object> requestBody = Map.of("answers", answers);

        return webClient.post()
                .uri(ragApiBaseUrl + "/interview/evaluate")
                .body(Mono.just(requestBody), Map.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }


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
}