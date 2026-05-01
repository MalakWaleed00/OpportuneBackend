package com.example.demo.southbound.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cv_internships")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CvInternship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cvId;

    private String title;
    private String company;
    private String duration;

    @Column(columnDefinition = "TEXT")
    private String summary;
}
