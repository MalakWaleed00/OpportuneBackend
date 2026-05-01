package com.example.demo.service.impl;
import com.example.demo.domain.dto.parsing.ParsingResponseDTO;
import com.example.demo.service.IParsingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ParsingServiceImpl implements IParsingService {
    private final RestTemplate restTemplate;

    public ParsingResponseDTO parse(String cvText, String jobDescription) {

        String url = "http://ai-api/parse";

        Map<String, String> request = Map.of(
                "cv", cvText,
                "jobDescription", jobDescription
        );

        ResponseEntity<ParsingResponseDTO> response =
                restTemplate.postForEntity(url, request, ParsingResponseDTO.class);

        return response.getBody();
    }
}
