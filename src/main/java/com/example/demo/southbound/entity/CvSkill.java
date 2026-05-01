package com.example.demo.southbound.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cv_skills")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CvSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cvId;

    private String skill;
}
