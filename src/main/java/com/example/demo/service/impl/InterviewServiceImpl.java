package com.example.demo.service.impl;

import com.example.demo.service.IInterviewService;
import com.example.demo.service.ai.QuestionGenerationRouter;
import com.example.demo.service.ai.QuestionGenerationService;
import com.example.demo.southbound.Enum.QuestionFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements IInterviewService {
    private final QuestionGenerationRouter router;

    public void generateQuestions(
            String topic,
            QuestionFormat format,
            int number
    ) {

        QuestionGenerationService service =
                router.getService(format);

        service.generateQuestions(topic, format, number);
    }
}
