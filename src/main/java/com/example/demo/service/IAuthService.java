package com.example.demo.service;

import com.example.demo.domain.dto.auth.AuthResponseDTO;
import com.example.demo.domain.dto.auth.LoginRequestDTO;
import com.example.demo.domain.dto.auth.RegisterRequestDTO;

public interface IAuthService {
    AuthResponseDTO register(RegisterRequestDTO request);
    AuthResponseDTO login(LoginRequestDTO request);
}
