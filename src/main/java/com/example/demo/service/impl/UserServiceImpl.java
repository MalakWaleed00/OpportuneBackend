package com.example.demo.service.impl;

import com.example.demo.southbound.entity.User;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IUserService; // Ensure this interface exists in 'service' folder
import com.example.demo.domain.dto.auth.AuthResponseDTO; // Or create a specific UserDTO in domain.dto
import com.example.demo.southbound.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {


    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Make sure this is injected!

    @Autowired
    private UserMapper userMapper;

    @Override
    public AuthResponseDTO getUserProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Change this line to match your mapper method name
        return userMapper.toAuthResponse(user);
    }


    @Override
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        // 1. Find the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Verify the current password matches what is in the DB
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        // 3. Encrypt the new password and save it
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}