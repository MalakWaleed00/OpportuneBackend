package com.example.demo.service.ai.impl;

import com.example.demo.service.ai.QuestionGenerationService;
import com.example.demo.southbound.Enum.QuestionFormat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagQuestionService implements QuestionGenerationService {

    @Override
    public List<String> generateQuestions(
            String topic,
            QuestionFormat format,
            int numberOfQuestions
    ) {

        // Call RAG model here

        System.out.println("Using RAG model");

        return List.of();
    }
}