package com.example.demo.service.ai;

import com.example.demo.southbound.Enum.QuestionFormat;

import java.util.List;

public interface QuestionGenerationService {
    List<String> generateQuestions(
            String topic,
            QuestionFormat format,
            int numberOfQuestions
    );
}
