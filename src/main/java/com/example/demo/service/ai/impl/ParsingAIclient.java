package com.example.demo.service.ai.impl;

import com.example.demo.domain.dto.parsing.CvDTO;
import com.example.demo.service.ai.ParsingModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.nio.charset.StandardCharsets;

@Service
public class ParsingAIclient implements ParsingModel {

    private final WebClient webClient;

    public ParsingAIclient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8003")
                .build();
    }

    @Override
    public CvDTO parseCV(MultipartFile file) throws IOException {

        try {

            ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };

            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("file", resource)
                    .filename(file.getOriginalFilename());

            String rawJson = webClient.post()
                    .uri("/parse/cv/")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToMono(String.class) // Ask for a String, not an object
                    .block();

            System.out.println(("APII KEY: "+System.getenv("GEMINI_API_KEY")));            // 2. Print it to your IntelliJ console
            System.out.println("DEBUG RAW JSON: " + rawJson);

            if (rawJson == null) {
                throw new RuntimeException("FastAPI returned null response");
            }

            if (rawJson.contains("\"error\"")) {
                throw new RuntimeException("Gemini API Error: " + rawJson);
            }
            // 3. Now let Jackson try to map it (or use an ObjectMapper)
            return new ObjectMapper().readValue(rawJson, CvDTO.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<String> parseJobDescriptionSkills(String jobDescription) {
        try {
            ByteArrayResource txtFile = new ByteArrayResource(jobDescription.getBytes()) {
                @Override
                public String getFilename() {
                    return "job_description.txt";
                }
            };

            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("file", txtFile).filename("job_description.txt");

            String rawJson = webClient.post()
                    .uri("/parse/jd/")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            if (rawJson == null) {
                throw new RuntimeException("Parsing model returned null for job description");
            }

            System.out.println("DEBUG JD RAW JSON: " + rawJson);

            Map<String, Object> parsed = new ObjectMapper().readValue(rawJson, new TypeReference<>() {});

            List<String> skills = new ArrayList<>();
            List<String> required = (List<String>) parsed.get("required_skills");
            List<String> preferred = (List<String>) parsed.get("preferred_skills");
            if (required != null) skills.addAll(required);
            if (preferred != null) skills.addAll(preferred);
            return skills;

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse job description skills", e);
        }
    }
}