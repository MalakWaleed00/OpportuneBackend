package com.example.demo.northbound.controller;

import com.example.demo.domain.dto.interview.GenerateQuestionsRequestDTO;
import com.example.demo.domain.dto.interview.GenerateQuestionsResponseDTO;
import com.example.demo.service.IInterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/interview")
@RequiredArgsConstructor
public class InterviewController {

    private final IInterviewService interviewService;

    @PostMapping("/generate")
    public ResponseEntity<GenerateQuestionsResponseDTO> generateQuestions(
            @RequestBody GenerateQuestionsRequestDTO request
    ) {
        GenerateQuestionsResponseDTO response = interviewService.generateQuestions(
                request.getJob_description()
        );

        return ResponseEntity.ok(response);
    }
}
