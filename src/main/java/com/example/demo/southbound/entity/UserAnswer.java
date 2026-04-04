package com.example.demo.southbound.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_answers")
@Getter
@Setter
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private InterviewQuestion question;

    @ManyToOne
    private User user;

    private String answer;

    private Boolean correct;
}