package com.example.demo.domain.dto.interview;

import lombok.*;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class McqEvaluationReportDTO {
    private String status;
    private double overallScore;
    private String overallGrade;
    private Map<String, SkillSummaryDTO> skillSummary;
    private List<QuestionResultDTO> questionResults;

    @Getter
    @Setter
    public static class SkillSummaryDTO {
        private String skill;
        private double avgScore;
        private String grade;
        private int numQuestions;
    }

    @Getter
    @Setter
    public static class QuestionResultDTO {
        private String questionId;
        private String skill;
        private String question;
        private String userAnswer;
        private String correctAnswer;
        private String grade;
        private double hybridScore;
    }
}