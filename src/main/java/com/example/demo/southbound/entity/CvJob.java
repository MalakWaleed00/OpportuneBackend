package com.example.demo.southbound.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cv_jobs")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CvJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cvId;

    private String title;
    private String company;

    @Column(columnDefinition = "TEXT")
    private String summary;
}

