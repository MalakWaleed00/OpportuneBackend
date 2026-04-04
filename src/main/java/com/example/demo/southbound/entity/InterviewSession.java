package com.example.demo.southbound.entity;

import com.example.demo.southbound.Enum.QuestionFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "interview_sessions")
@Getter
@Setter
public class InterviewSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String topic;

    @Enumerated(EnumType.STRING)
    private QuestionFormat questionFormat;

    private Integer numberOfQuestions;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<InterviewQuestion> questions;
}
