package com.example.demo.domain.dto.interview;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO for submitting user answers for evaluation.
 *
 * File location: src/main/java/com/example/demo/domain/dto/interview/EvaluateAnswersRequestDTO.java
 * → This is a NEW file. Create it inside the interview DTO package.
 */
@Getter
@Setter
public class EvaluateAnswersRequestDTO {

    /**
     * List of answered questions.
     * Each item must include cluster_id, skills, question,
     * user_answer, and correct_answer (from the generate step).
     */
    private List<AnswerItemDTO> answers;

    @Getter
    @Setter
    public static class AnswerItemDTO {
        private int          clusterId;
        private List<String> skills;
        private String       question;
        private String       userAnswer;
        private String       correctAnswer;
    }
}