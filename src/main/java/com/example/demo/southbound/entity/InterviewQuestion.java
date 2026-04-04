package com.example.demo.southbound.entity;

import com.example.demo.southbound.Enum.QuestionFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "interview_questions")
@Getter
@Setter
public class InterviewQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;

    @Enumerated(EnumType.STRING)
    private QuestionFormat format;

    @ElementCollection
    private List<String> options;

    private String correctAnswer;

    @ManyToOne
    private InterviewSession session;
}
