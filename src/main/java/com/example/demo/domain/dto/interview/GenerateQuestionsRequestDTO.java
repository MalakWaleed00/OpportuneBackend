package com.example.demo.domain.dto.interview;

import com.example.demo.southbound.Enum.QuestionFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateQuestionsRequestDTO {
    private String job_description;


    public String getJob_description() {
        return job_description;
    }
}