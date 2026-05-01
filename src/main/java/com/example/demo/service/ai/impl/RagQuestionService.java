package com.example.demo.service.ai.impl;

import com.example.demo.domain.dto.interview.GenerateQuestionsResponseDTO;
import com.example.demo.service.ai.QuestionGenerationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Service
public class RagQuestionService implements QuestionGenerationService {

    @Value("${interview.api.url}")
    private String apiUrl;
    private final RestTemplate restTemplate;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RagQuestionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public GenerateQuestionsResponseDTO generateQuestions(String jobDescription) {

        String url = apiUrl + "/interview";

        Map<String, Object> requestBody = Map.of(
                "job_description", jobDescription
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("ngrok-skip-browser-warning", "true");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<GenerateQuestionsResponseDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    GenerateQuestionsResponseDTO.class
            );

            return response.getBody();

        } catch (Exception e) {
            e.printStackTrace();
            return new GenerateQuestionsResponseDTO(
                    "error",
                    List.of(),
                    List.of()
            );
        }
    }

}