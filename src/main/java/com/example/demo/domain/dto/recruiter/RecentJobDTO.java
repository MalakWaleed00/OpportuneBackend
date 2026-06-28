package com.example.demo.domain.dto.recruiter;

import com.example.demo.southbound.Enum.JobPostingStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RecentJobDTO {
    private Long id;
    private String title;
    private String location;
    private JobPostingStatus status;
    private Integer applicationCount;
    private LocalDateTime postedAt;
}