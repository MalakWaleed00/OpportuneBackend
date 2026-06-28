package com.example.demo.domain.dto.application;

import com.example.demo.southbound.Enum.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ApplicationAnalyticsDTO {
    private int total;
    private int active;
    private int interviews;
    private int offers;
    private int accepted;
    private int rejected;
    private int withdrawn;
    private int interviewRate;
    private int offerRate;
    private int successRate;
    private Map<ApplicationStatus, Long> statusBreakdown;
    private List<ApplicationSummaryDTO> recentApplications;
}