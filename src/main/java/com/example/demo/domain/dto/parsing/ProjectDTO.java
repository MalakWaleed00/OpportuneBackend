package com.example.demo.domain.dto.parsing;
import lombok.*;
import java.util.List;

@Getter @Setter
public class ProjectDTO {
    private String name;
    private String description;
    private List<String> technologies;
}