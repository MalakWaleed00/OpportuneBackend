package com.example.demo.domain.dto.job;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendationRequestDTO {

    private Long userId;

    private int limit;
}