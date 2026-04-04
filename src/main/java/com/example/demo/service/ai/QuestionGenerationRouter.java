package com.example.demo.service.ai;

import com.example.demo.service.ai.impl.GeminiQuestionService;
import com.example.demo.service.ai.impl.RagQuestionService;
import com.example.demo.southbound.Enum.QuestionFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionGenerationRouter {

    private final GeminiQuestionService geminiService;
    private final RagQuestionService ragService;

    public QuestionGenerationService getService(QuestionFormat format) {

        if (format == QuestionFormat.ESSAY) {
            return ragService;
        }

        return geminiService;
    }
}