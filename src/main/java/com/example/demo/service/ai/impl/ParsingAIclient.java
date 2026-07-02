package com.example.demo.service.ai.impl;

import com.example.demo.domain.dto.parsing.CvDTO;
import com.example.demo.service.ai.ParsingModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Service
public class ParsingAIclient implements ParsingModel {

    private final WebClient webClient;

    public ParsingAIclient(@Value("${parsing.api.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
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
            builder.part("file", resource).filename(file.getOriginalFilename());

            String rawJson = webClient.post()
                    .uri("/parse/cv/")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            if (rawJson == null) {
                throw new RuntimeException("Parsing model returned null response");
            }

            return new ObjectMapper().readValue(rawJson, CvDTO.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}