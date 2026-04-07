package com.example.demo.service.impl;

import com.example.demo.domain.dto.job.ApplyOptionDTO;
import com.example.demo.service.ISerpApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.demo.domain.dto.job.JobDetails;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
@Service
@RequiredArgsConstructor
public class SerpApiImpl implements ISerpApiService {
    private final RestTemplate restTemplate;

    private final String API_KEY = "b03dd470e3b1b07f37ca5d2c90cab27c7d9d0ef5fc96b8d81da3cecf19e72692";

    @Override
    public List<JobDetails> searchJobs(String jobTitle, List<String> skills) {

        // ✅ FIX: handle null/empty title
        String rawQuery = (jobTitle == null || jobTitle.isBlank()) ? "jobs" : jobTitle;

        String query = URLEncoder.encode(rawQuery, StandardCharsets.UTF_8);

        String url = "https://serpapi.com/search.json?engine=google_jobs&q="
                + query + "&api_key=" + API_KEY;

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            Map<String, Object> body = response.getBody();

            List<Map<String, Object>> jobs =
                    (List<Map<String, Object>>) body.get("jobs_results");

            List<JobDetails> result = new ArrayList<>();

            for (Map<String, Object> job : jobs) {

                String title = (String) job.get("title");
                String company = (String) job.get("company_name");
                String location = (String) job.get("location");
                String via = (String) job.get("via");
                String shareLink = (String) job.get("share_link");
                String thumbnail = (String) job.get("thumbnail");
                String description = (String) job.get("description");

                List<String> extensions =
                        (List<String>) job.getOrDefault("extensions", new ArrayList<>());

                List<Map<String, Object>> applyList =
                        (List<Map<String, Object>>) job.getOrDefault("apply_options", new ArrayList<>());

                List<ApplyOptionDTO> applyOptions = new ArrayList<>();

                for (Map<String, Object> app : applyList) {
                    applyOptions.add(new ApplyOptionDTO(
                            (String) app.get("title"),
                            (String) app.get("link")
                    ));
                }

                result.add(new JobDetails(
                        title,
                        company,
                        location,
                        via,
                        shareLink,
                        thumbnail,
                        extensions,
                        description,
                        applyOptions
                ));
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}