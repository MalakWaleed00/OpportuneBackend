package com.example.demo.domain.dto.auth;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {

    private String token;

    private String username;

    private String email;

    private String name;

    private String role;

    private String location;

    private String cvLink;

    private String profilePicLink;

    private Set<String> skills;

    private boolean active;

}
