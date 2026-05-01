package com.example.demo.service.impl;

import com.example.demo.domain.dto.interview.GenerateQuestionsResponseDTO;
import com.example.demo.service.IInterviewService;
//import com.example.demo.service.ai.QuestionGenerationRouter;
import com.example.demo.service.ai.impl.RagQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements IInterviewService {
    private final RagQuestionService service;



    @Override
    public GenerateQuestionsResponseDTO generateQuestions(String jobDescription) {
        return service.generateQuestions(jobDescription);
    }
}
