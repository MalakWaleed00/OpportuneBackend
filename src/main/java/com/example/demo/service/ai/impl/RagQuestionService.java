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
            int number
    ) {

        System.out.println("Using RAG model");

        // 1. Retrieve context
        List<String> context = List.of(
                "Key concepts of " + topic,
                "Common interview discussions about " + topic
        );

        // 2. Build prompt
        String prompt = "Generate " + number + " essay interview questions about " + topic +
                " using the following context:\n" +
                String.join("\n", context);

        // 3. Call model (mock for now)
        String response = "Q1...\nQ2...\nQ3...";

        // 4. Parse response
        return List.of(response.split("\n"));
    }
}