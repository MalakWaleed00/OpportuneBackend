package com.example.demo.domain.dto.auth;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {

    private String name;
    private String email;
    private String password;

    private String role;          // JOB_SEEKER or RECRUITER

    private String cvLink;
    private String profilePicLink;

    private String country;

    private Set<String> skills;

    private String experienceLevel;


}
