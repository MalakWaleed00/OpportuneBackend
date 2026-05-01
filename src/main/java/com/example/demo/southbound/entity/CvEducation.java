package com.example.demo.southbound.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "cv_education")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CvEducation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cvId;

    private String degree;
    private String institution;
    private String year;
}
