package com.example.demo.service;

import com.example.demo.southbound.Enum.QuestionFormat;

public interface IInterviewService {
    void generateQuestions(
            String topic,
            QuestionFormat format,
            int number
    );
}
