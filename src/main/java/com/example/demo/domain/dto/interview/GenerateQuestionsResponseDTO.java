package com.example.demo.domain.dto.interview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateQuestionsResponseDTO {
    private String status;
    private List<String> skills;
    private List<InterviewQuestionDTO> questions;


}