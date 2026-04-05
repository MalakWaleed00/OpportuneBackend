package com.example.demo.northbound.controller;

import com.example.demo.domain.dto.auth.AuthResponseDTO;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173") // This lets your React app talk to Spring
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<AuthResponseDTO> getUserProfile(@PathVariable Long id) {
        // Calls the service implementation we just fixed!
        AuthResponseDTO response = userService.getUserProfile(id);
        return ResponseEntity.ok(response);
    }
}