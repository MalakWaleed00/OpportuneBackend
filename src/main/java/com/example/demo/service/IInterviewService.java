package com.example.demo.service;

import com.example.demo.domain.dto.interview.GenerateQuestionsResponseDTO;
import com.example.demo.southbound.Enum.QuestionFormat;

public interface IInterviewService {
    GenerateQuestionsResponseDTO generateQuestions(String jobDescription);
}
