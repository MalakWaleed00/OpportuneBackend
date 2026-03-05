package com.example.demo.southbound.mapper;


import com.example.demo.domain.dto.auth.RegisterRequestDTO;
import com.example.demo.southbound.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(RegisterRequestDTO dto) {

        return User.builder()
                .username(dto.getEmail())        // using email as username
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .location(dto.getCountry())
                .cvLink(dto.getCvLink())
                .profilePicLink(dto.getProfilePicLink())
                .isActive(true)
                .build();
    }

}
