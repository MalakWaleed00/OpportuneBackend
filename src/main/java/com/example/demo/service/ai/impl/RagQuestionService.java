package com.example.demo.service.ai.impl;

import com.example.demo.service.ai.QuestionGenerationService;
import com.example.demo.southbound.Enum.QuestionFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class RagQuestionService implements QuestionGenerationService {

    // ── Put your ngrok URL in application.properties ──────────
    // interview.api.url=https://your-ngrok-url.ngrok-free.dev
    @Value("https://demetrice-unpitiful-blunderingly.ngrok-free.dev")
    private String apiUrl;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<String> generateQuestions(
            String topic,
            QuestionFormat format,
            int number
    ) {
        System.out.println("Using RAG model — calling Python FastAPI...");

        try {
            // ── Step 1: Build request body ────────────────────
            String requestBody = objectMapper.writeValueAsString(
                    java.util.Map.of("job_description", topic)
            );

            // ── Step 2: Call your FastAPI /interview endpoint ─
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + "/interview"))
                    .header("Content-Type", "application/json")
                    .header("ngrok-skip-browser-warning", "true")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            // ── Step 3: Parse response ────────────────────────
            if (response.statusCode() != 200) {
                System.err.println("API error: " + response.statusCode());
                System.err.println("Body: " + response.body());
                return List.of("Error generating questions — API returned " + response.statusCode());
            }

            JsonNode root = objectMapper.readTree(response.body());

            // Check status
            String status = root.path("status").asText();
            if (!status.equals("questions_ready")) {
                return List.of("Unexpected status from API: " + status);
            }

            // ── Step 4: Extract questions ─────────────────────
            List<String> questions = new ArrayList<>();
            JsonNode questionsNode = root.path("questions");

            for (JsonNode q : questionsNode) {
                String question     = q.path("question").asText();
                String answer       = q.path("answer").asText();
                String clusterId    = q.path("cluster_id").asText();
                JsonNode skillsNode = q.path("skills");

                // Build skills string
                List<String> skills = new ArrayList<>();
                for (JsonNode skill : skillsNode) {
                    skills.add(skill.asText());
                }

                // Format question entry
                String entry = String.format(
                        "[Cluster %s | Skills: %s]\nQ: %s\nA: %s",
                        clusterId,
                        String.join(", ", skills),
                        question,
                        answer
                );

                questions.add(entry);

                // Stop if we have enough questions
                if (questions.size() >= number) break;
            }

            System.out.println("✅ Generated " + questions.size() + " questions from API");
            return questions;

        } catch (Exception e) {
            System.err.println("❌ Error calling interview API: " + e.getMessage());
            e.printStackTrace();
            return List.of("Error: " + e.getMessage());
        }
    }


    // ── Evaluation method ─────────────────────────────────────
    // Call this after user answers all questions
    public JsonNode evaluateAnswers(String jobDescription, List<java.util.Map<String, Object>> answers) {
        try {
            java.util.Map<String, Object> requestBody = java.util.Map.of(
                    "job_description", jobDescription,
                    "answers", answers
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + "/interview"))
                    .header("Content-Type", "application/json")
                    .header("ngrok-skip-browser-warning", "true")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            objectMapper.writeValueAsString(requestBody)
                    ))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            return objectMapper.readTree(response.body());

        } catch (Exception e) {
            System.err.println("❌ Error evaluating answers: " + e.getMessage());
            return null;
        }
    }
}