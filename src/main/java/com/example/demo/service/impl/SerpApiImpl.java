package com.example.demo.service.impl;

import com.example.demo.service.ISerpApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
@Service
@RequiredArgsConstructor
public class SerpApiImpl implements ISerpApiService {
    private final RestTemplate restTemplate;

    private final String API_KEY = "b03dd470e3b1b07f37ca5d2c90cab27c7d9d0ef5fc96b8d81da3cecf19e72692";

    @Override
    public List<String> searchJobs(String jobTitle, List<String> skills) {

        // build search query
        String query = jobTitle + " " + String.join(" ", skills) + "job";

        String url = "https://serpapi.com/search.json?q=" + query + "&api_key=" + API_KEY;

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            Map<String, Object> body = response.getBody();

            List<Map<String, Object>> results =
                    (List<Map<String, Object>>) body.get("organic_results");

            List<String> links = new ArrayList<>();

            for (Map<String, Object> result : results) {
                links.add((String) result.get("link"));
            }

            return links;

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
