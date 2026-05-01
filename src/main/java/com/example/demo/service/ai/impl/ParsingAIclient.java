package com.example.demo.service.ai.impl;

import com.example.demo.domain.dto.parsing.CvDTO;
import com.example.demo.service.ai.ParsingModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ParsingAIclient implements ParsingModel {

    private final WebClient webClient;

    public ParsingAIclient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8000")
                .build();
    }

    @Override
    public CvDTO parseCV(MultipartFile file) {

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
                    .uri("/parse/cv")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToMono(String.class) // Ask for a String, not an object
                    .block();

            System.out.println(("APII KEY: "+System.getenv("GEMINI_API_KEY")));            // 2. Print it to your IntelliJ console
            System.out.println("DEBUG RAW JSON: " + rawJson);
            if (rawJson.contains("\"error\"")) {
                throw new RuntimeException("Gemini API Error: " + rawJson);
            }
            // 3. Now let Jackson try to map it (or use an ObjectMapper)
            return new ObjectMapper().readValue(rawJson, CvDTO.class);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send PDF to AI", e);
        }
    }
}