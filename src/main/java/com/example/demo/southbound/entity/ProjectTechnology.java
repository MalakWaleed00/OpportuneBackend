package com.example.demo.southbound.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "project_technologies")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ProjectTechnology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long projectId;

    private String technology;
}
