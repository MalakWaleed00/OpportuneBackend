package com.example.demo.service.ai;

import com.example.demo.domain.dto.interview.GenerateQuestionsResponseDTO;
import com.example.demo.domain.dto.interview.QuestionResponseDTO;
import com.example.demo.southbound.Enum.QuestionFormat;

import java.util.List;

public interface QuestionGenerationService {
    List<QuestionResponseDTO> generateQuestions(String job_description);
}
