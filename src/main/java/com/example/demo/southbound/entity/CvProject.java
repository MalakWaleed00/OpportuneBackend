package com.example.demo.southbound.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cv_projects")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CvProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cvId;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}
