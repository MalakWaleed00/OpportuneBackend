package com.example.demo.service.impl;

import com.example.demo.domain.dto.auth.AuthResponseDTO;
import com.example.demo.domain.dto.auth.RegisterRequestDTO;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.repository.ISkillRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IAuthService;
import com.example.demo.service.IJwtService;
import com.example.demo.southbound.entity.Role;
import com.example.demo.southbound.entity.Skill;
import com.example.demo.southbound.entity.User;
import com.example.demo.southbound.entity.UserSkill;
import com.example.demo.southbound.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements IAuthService {
    private  final IUserRepository userRepository;
    private final IJwtService jwtService;
    private final UserMapper userMapper;
    private final IRoleRepository roleRepository;
    private final ISkillRepository skillRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

// map basic fields
        User user = userMapper.toEntity(request);

// encode password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

// assign role
        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRole(role);

// handle skills
        if (request.getSkills() != null) {

            for (String skillName : request.getSkills()) {

                Skill skill = skillRepository
                        .findByName(skillName)
                        .orElseGet(() -> skillRepository.save(
                                Skill.builder().name(skillName).build()
                        ));

                UserSkill userSkill = UserSkill.builder()
                        .user(user)
                        .skill(skill)
                        .build();

                user.getUserSkills().add(userSkill);
            }
        }

// save user
        userRepository.save(user);

// generate jwt
        String token = jwtService.generateToken(user);

        return AuthResponseDTO.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .build();

    }

}
