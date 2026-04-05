package com.example.demo.service.impl;

import com.example.demo.common.exception.EmailAlreadyExistsException;
import com.example.demo.common.exception.InvalidCredentialsException;
import com.example.demo.common.exception.RoleNotFoundException;
import com.example.demo.domain.dto.auth.AuthResponseDTO;
import com.example.demo.domain.dto.auth.LoginRequestDTO;
import com.example.demo.domain.dto.auth.RegisterRequestDTO;
import com.example.demo.repository.IGroupRepository;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.repository.ISkillRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IAuthService;
import com.example.demo.service.IJwtService;
import com.example.demo.southbound.entity.Group;
import com.example.demo.southbound.entity.Role;
import com.example.demo.southbound.entity.Skill;
import com.example.demo.southbound.entity.User;
import com.example.demo.southbound.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements IAuthService {

    private final IUserRepository userRepository;
    private final IJwtService jwtService;
    private final UserMapper userMapper;
    private final IRoleRepository roleRepository;
    private final ISkillRepository skillRepository;
    private final PasswordEncoder passwordEncoder;
    private final IGroupRepository groupRepository;

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        user.setRole(role);

        Group group = groupRepository.findByName(request.getRole())
                .orElseThrow(() -> new RuntimeException("Group not found"));

        user.setGroup(group);

        if (request.getSkills() != null && !request.getSkills().isEmpty()) {

            if (user.getUserSkills() == null) {
                user.setUserSkills(new HashSet<>());
            }

            Set<String> uniqueSkills = request.getSkills()
                    .stream()
                    .map(skill -> skill.trim().toLowerCase())
                    .filter(skill -> !skill.isEmpty())
                    .collect(java.util.stream.Collectors.toSet());

            for (String skillName : uniqueSkills) {

                Skill skill = getOrCreateSkill(skillName);

                user.getUserSkills().add(
                        userMapper.toUserSkill(user, skill)
                );
            }
        }

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        AuthResponseDTO response = userMapper.toAuthResponse(user);
        response.setToken(token);

        return response;
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        AuthResponseDTO response = userMapper.toAuthResponse(user);
        response.setToken(token);

        return response;
    }

    // ==================================================
    // Helper Methods
    // ==================================================

    private Skill getOrCreateSkill(String skillName) {
        return skillRepository.findByName(skillName)
                .orElseGet(() -> skillRepository.save(
                        Skill.builder()
                                .name(skillName)
                                .build()
                ));
    }
}