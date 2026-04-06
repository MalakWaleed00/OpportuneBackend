package com.example.demo.domain.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProfileRequestDTO {

    private String username;

    private String email;

    private String name;

    private String location;

    private String cvLink;

    private String profilePicLink;

    private Set<String> skills;
}
