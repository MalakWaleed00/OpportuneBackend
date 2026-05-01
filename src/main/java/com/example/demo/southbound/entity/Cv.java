package com.example.demo.southbound.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cv")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Cv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String name;
    private String email;
    private String phone;
    private String experienceLevel;
}
