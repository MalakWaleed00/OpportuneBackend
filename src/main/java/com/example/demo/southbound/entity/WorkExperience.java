package com.example.demo.southbound.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "work_experiences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;
    private String position;
    private String startDate;
    private String endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
