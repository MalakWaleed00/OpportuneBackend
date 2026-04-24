package com.example.demo.domain.dto.interview;

import lombok.*;
import java.util.List;

@Getter
@Setter
public class McqEvaluateRequestDTO {
    private String sessionId;
    private List<AnswerItemDTO> answers;

    @Getter
    @Setter
    public static class AnswerItemDTO {
        private String questionId;
        private String userAnswer; // "a", "b", "c", or "d"
    }
}