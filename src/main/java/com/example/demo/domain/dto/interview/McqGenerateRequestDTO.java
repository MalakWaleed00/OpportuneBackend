package com.example.demo.domain.dto.interview;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class McqGenerateRequestDTO {
    @JsonProperty("jobDescription")
    private String jobDescription;
}