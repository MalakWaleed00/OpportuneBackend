package com.example.demo.service.impl;

import com.example.demo.southbound.entity.User;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IUserService; // Ensure this interface exists in 'service' folder
import com.example.demo.domain.dto.auth.AuthResponseDTO; // Or create a specific UserDTO in domain.dto
import com.example.demo.southbound.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public AuthResponseDTO getUserProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Change this line to match your mapper method name
        return userMapper.toAuthResponse(user);
    }
}