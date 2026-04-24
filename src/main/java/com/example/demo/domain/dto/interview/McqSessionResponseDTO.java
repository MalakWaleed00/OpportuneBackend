package com.example.demo.domain.dto.interview;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class McqSessionResponseDTO {
    private String status;
    private String sessionId;
    private List<String> skills;
    private List<McqQuestionDTO> questions;
}