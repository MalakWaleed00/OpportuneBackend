package com.example.demo.domain.dto.interview;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * DTO returned to the frontend after answer evaluation.
 *
 * File location: src/main/java/com/example/demo/domain/dto/interview/EvaluationReportDTO.java
 * → This is a NEW file. Create it inside the interview DTO package.
 */
@Getter
@Setter
public class EvaluationReportDTO {

    private String              status;
    private double              overallScore;
    private String              overallGrade;

    /** Key = cluster id as string ("0", "1" ...) */
    private Map<String, ClusterResultDTO> clusters;

    @Getter
    @Setter
    public static class ClusterResultDTO {
        private int          clusterId;
        private List<String> skills;
        private double       avgScore;
        private String       grade;
        private List<String> weakMetrics;
    }
}