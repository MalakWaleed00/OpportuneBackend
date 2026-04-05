package com.example.demo.service.ai.impl;
import com.example.demo.service.ISerpApiService;
import com.example.demo.service.ai.JobRecommendationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import com.example.demo.domain.dto.job.JobResponseDTO;

@Service
@RequiredArgsConstructor
public class AiJobRecommendationModel implements JobRecommendationModel {

    private final RestTemplate restTemplate;
    private final ISerpApiService serpApiService;

    @Override
    public List<JobResponseDTO> recommendJobs(List<String> skills, String experience, int topK) {

        String url = "http://localhost:8000/recommend";

        // Correct field names
        Map<String, Object> requestBody = Map.of(
                "user_skills", skills,
                "user_experience", experience,
                "topK", topK
        );

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            Map<String, Object> body = response.getBody();

            List<Map<String, Object>> recommendations =
                    (List<Map<String, Object>>) body.get("recommendations");

            List<JobResponseDTO> result = new ArrayList<>();

            for (Map<String, Object> rec : recommendations) {

                String title = (String) rec.get("job_title");
                List<String> contributingSkills =
                        (List<String>) rec.get("contributing_skills");
                List<String> jobs = serpApiService.searchJobs(title, contributingSkills);

                result.add(new JobResponseDTO(title, contributingSkills,jobs));
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }





}
