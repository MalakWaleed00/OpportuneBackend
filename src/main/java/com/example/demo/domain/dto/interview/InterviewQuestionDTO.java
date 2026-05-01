package com.example.demo.domain.dto.interview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewQuestionDTO {
    private Integer cluster_id;
    private List<String> skills;
    private String question;
    private String answer;
}
