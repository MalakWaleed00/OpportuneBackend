package com.example.demo.domain.dto.auth;

import lombok.*;

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

    private String profilePicLink;

    private boolean active;

}
