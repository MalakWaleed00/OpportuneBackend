package com.example.demo.domain.dto.interview;

import com.example.demo.southbound.Enum.QuestionFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateQuestionsRequestDTO {

    private String topic;
    private QuestionFormat questionFormat;
    private int numberOfQuestions;
}