package com.example.demo.service.ai.impl;


import com.example.demo.domain.dto.interview.GenerateQuestionsResponseDTO;
import com.example.demo.service.ai.QuestionGenerationService;
import com.example.demo.southbound.Enum.QuestionFormat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeminiQuestionService implements QuestionGenerationService {

    @Override
    public GenerateQuestionsResponseDTO generateQuestions(String job_description) {

        // Call Gemini API here

        System.out.println("Using Gemini model");

        return new GenerateQuestionsResponseDTO();
    }
}
