package com.example.demo.service.ai.impl;

import com.example.demo.domain.dto.course.CourseDTO;
import com.example.demo.domain.dto.course.CourseRecommendationRequest;
import com.example.demo.service.ai.CourseRecommendationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiCourseRecommendationClient implements CourseRecommendationModel {

    private final RestTemplate restTemplate;

    private static final String URL = "http://localhost:8004/recommend";

    @Override
    public List<CourseDTO> recommendCourses(CourseRecommendationRequest request) {

        Map<String, Object> body = Map.of(
                "missing_skills", request.getMissingSkills(),
                "experience_level", request.getExperienceLevel(),
                "topK", request.getTopK()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    URL,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            Map<String, Object> responseBody = response.getBody();
            if (responseBody == null) return new ArrayList<>();

            System.out.println("DEBUG course response: " + responseBody);

            List<Map<String, Object>> recs =
                    (List<Map<String, Object>>) responseBody.get("recommendations");

            if (recs == null) return new ArrayList<>();

            List<CourseDTO> result = new ArrayList<>();
            for (Map<String, Object> rec : recs) {
                CourseDTO course = new CourseDTO();
                course.setCourseName((String) rec.get("course_name"));
                course.setUrl((String) rec.get("url"));
                course.setMatchedSkill((String) rec.get("matched_skill"));
                course.setMissingSkill((String) rec.get("missing_skill"));
                course.setMatchScore(rec.get("match_score") != null ? Double.parseDouble(rec.get("match_score").toString()) : null);
                course.setDifficulty((String) rec.get("difficulty"));
                course.setRating(rec.get("rating") != null ? Double.parseDouble(rec.get("rating").toString()) : null);
                result.add(course);
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}