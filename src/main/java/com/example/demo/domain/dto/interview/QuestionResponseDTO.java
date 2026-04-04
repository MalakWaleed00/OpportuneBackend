package com.example.demo.domain.dto.interview;

import com.example.demo.southbound.Enum.QuestionFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionResponseDTO {

    private Long id;
    private String questionText;
    private QuestionFormat type;
    private List<String> options;
}
