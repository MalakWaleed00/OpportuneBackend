package com.example.demo.service;

import com.example.demo.southbound.entity.User;
// If you have a specific ProfileDTO, import it here.
// For now, let's use the User entity or a generic Object to stop the error.
import com.example.demo.domain.dto.auth.AuthResponseDTO;

public interface IUserService {

    // This defines the method that UserServiceImpl will fill out
    AuthResponseDTO getUserProfile(Long id);

}