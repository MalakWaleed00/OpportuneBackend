package com.example.demo.domain.dto.job;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RecommendedJobDTO {

    private Long jobId;

    private String title;

    private String company;

    private String location;

    private String description;

    private Double minSalary;

    private Double maxSalary;

    private Set<String> skills;

    private Double score;
}
