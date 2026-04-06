package com.example.demo.service;

import com.example.demo.domain.dto.auth.AuthResponseDTO;
import com.example.demo.domain.dto.auth.UpdateProfileRequestDTO;

public interface IUserService {

    AuthResponseDTO getUserProfile(Long id);
    AuthResponseDTO getCurrentUserProfile();
    AuthResponseDTO updateUserProfile(Long id, UpdateProfileRequestDTO request);
    AuthResponseDTO updateCurrentUserProfile(UpdateProfileRequestDTO request);
    void changePassword(Long userId, String currentPassword, String newPassword);
    void changeCurrentUserPassword(String currentPassword, String newPassword);
}
