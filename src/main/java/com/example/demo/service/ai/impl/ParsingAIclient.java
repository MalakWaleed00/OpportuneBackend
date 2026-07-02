package com.example.demo.service.ai.impl;

import com.example.demo.domain.dto.parsing.CvDTO;
import com.example.demo.service.ai.ParsingModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class ParsingAIclient implements ParsingModel {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ParsingAIclient(@Value("${parsing.api.base-url}") String baseUrl) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10_000)
                .responseTimeout(Duration.ofMinutes(3))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(3, TimeUnit.MINUTES)));

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Override
    public CvDTO parseCV(MultipartFile file) throws IOException {
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
                .onStatus(status -> !status.is2xxSuccessful(), response ->
                        response.bodyToMono(String.class).map(body ->
                                new RuntimeException("Parsing service error " + response.statusCode() + ": " + body)))
                .bodyToMono(String.class)
                .block();

        if (rawJson == null) {
            throw new RuntimeException("Parsing model returned empty response");
        }

        System.out.println("DEBUG parsing response: " + rawJson);

        try {
            return objectMapper.readValue(rawJson, CvDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CV response into DTO. Raw response: " + rawJson, e);
        }
    }
}