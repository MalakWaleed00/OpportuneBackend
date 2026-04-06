package com.example.demo.service.impl;

import com.example.demo.common.util.IAuthUtils;
import com.example.demo.domain.dto.auth.AuthResponseDTO;
import com.example.demo.domain.dto.auth.UpdateProfileRequestDTO;
import com.example.demo.repository.ISkillRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IUserService;
import com.example.demo.southbound.entity.Skill;
import com.example.demo.southbound.entity.User;
import com.example.demo.southbound.entity.UserSkill;
import com.example.demo.southbound.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements IUserService {


    private final IUserRepository userRepository;
    private final ISkillRepository skillRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final IAuthUtils authUtils;

    @Override
    public AuthResponseDTO getUserProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));

        return userMapper.toAuthResponse(user);
    }

    @Override
    public AuthResponseDTO getCurrentUserProfile() {
        return userMapper.toAuthResponse(authUtils.getCurrentUser());
    }

    @Override
    public AuthResponseDTO updateUserProfile(Long id, UpdateProfileRequestDTO request) {
        validateProfileOwner(id);
        return updateProfile(id, request);
    }

    @Override
    public AuthResponseDTO updateCurrentUserProfile(UpdateProfileRequestDTO request) {
        return updateProfile(authUtils.getCurrentUserId(), request);
    }

    private AuthResponseDTO updateProfile(Long id, UpdateProfileRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));

        if (request.getUsername() != null) {
            String username = normalizeRequiredField(request.getUsername(), "Username");
            if (!username.equalsIgnoreCase(user.getProfileUsername()) && userRepository.existsByUsername(username)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
            }
            user.setUsername(username);
        }

        if (request.getEmail() != null) {
            String email = normalizeRequiredField(request.getEmail(), "Email");
            if (!email.equalsIgnoreCase(user.getEmail()) && userRepository.existsByEmail(email)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
            }
            user.setEmail(email);
        }

        if (request.getName() != null) {
            user.setName(normalizeOptionalField(request.getName()));
        }

        if (request.getLocation() != null) {
            user.setLocation(normalizeOptionalField(request.getLocation()));
        }

        if (request.getCvLink() != null) {
            user.setCvLink(normalizeOptionalField(request.getCvLink()));
        }

        if (request.getProfilePicLink() != null) {
            user.setProfilePicLink(normalizeOptionalField(request.getProfilePicLink()));
        }

        if (request.getSkills() != null) {
            replaceSkills(user, request.getSkills());
        }

        return userMapper.toAuthResponse(userRepository.save(user));
    }

    @Override
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        validateProfileOwner(userId);

        changePasswordInternal(userId, currentPassword, newPassword);
    }

    @Override
    public void changeCurrentUserPassword(String currentPassword, String newPassword) {
        changePasswordInternal(authUtils.getCurrentUserId(), currentPassword, newPassword);
    }

    private void changePasswordInternal(Long userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    private void validateProfileOwner(Long requestedUserId) {
        Long currentUserId = authUtils.getCurrentUserId();
        if (!currentUserId.equals(requestedUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only update your own profile");
        }
    }

    private String normalizeRequiredField(String value, String fieldName) {
        String normalizedValue = value.trim();
        if (normalizedValue.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldName + " cannot be blank");
        }
        return normalizedValue;
    }

    private String normalizeOptionalField(String value) {
        String normalizedValue = value.trim();
        return normalizedValue.isEmpty() ? null : normalizedValue;
    }

    private void replaceSkills(User user, Set<String> requestedSkills) {
        if (user.getUserSkills() == null) {
            user.setUserSkills(new LinkedHashSet<>());
        } else {
            user.getUserSkills().clear();
        }

        Set<String> uniqueSkills = requestedSkills.stream()
                .map(String::trim)
                .map(String::toLowerCase)
                .filter(skill -> !skill.isEmpty())
                .collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new));

        for (String skillName : uniqueSkills) {
            Skill skill = getOrCreateSkill(skillName);
            UserSkill userSkill = userMapper.toUserSkill(user, skill);
            user.getUserSkills().add(userSkill);
        }
    }

    private Skill getOrCreateSkill(String skillName) {
        return skillRepository.findByName(skillName)
                .orElseGet(() -> skillRepository.save(
                        Skill.builder()
                                .name(skillName)
                                .build()
                ));
    }
}
