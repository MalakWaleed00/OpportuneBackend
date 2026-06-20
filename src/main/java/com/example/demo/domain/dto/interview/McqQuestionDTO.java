package com.example.demo.domain.dto.interview;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class McqQuestionDTO {
    private String questionId;
    private String skill;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
}