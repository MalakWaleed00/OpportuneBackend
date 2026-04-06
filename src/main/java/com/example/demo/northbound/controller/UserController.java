package com.example.demo.northbound.controller;

import com.example.demo.domain.dto.auth.AuthResponseDTO;
import com.example.demo.domain.dto.auth.ChangePasswordDTO;
import com.example.demo.domain.dto.auth.UpdateProfileRequestDTO;
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

    @GetMapping("/{id}/profile")
    public ResponseEntity<AuthResponseDTO> getUserProfile(@PathVariable Long id) {
        AuthResponseDTO response = userService.getUserProfile(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me/profile")
    public ResponseEntity<AuthResponseDTO> getCurrentUserProfile() {
        return ResponseEntity.ok(userService.getCurrentUserProfile());
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<AuthResponseDTO> updateUserProfile(
            @PathVariable Long id,
            @RequestBody UpdateProfileRequestDTO request) {
        return ResponseEntity.ok(userService.updateUserProfile(id, request));
    }

    @PutMapping("/me/profile")
    public ResponseEntity<AuthResponseDTO> updateCurrentUserProfile(
            @RequestBody UpdateProfileRequestDTO request) {
        return ResponseEntity.ok(userService.updateCurrentUserProfile(request));
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordDTO dto) {

        try {
            userService.changePassword(id, dto.currentPassword, dto.newPassword);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            // Return a 400 Bad Request if the old password was wrong
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/me/change-password")
    public ResponseEntity<?> changeCurrentUserPassword(@RequestBody ChangePasswordDTO dto) {
        try {
            userService.changeCurrentUserPassword(dto.currentPassword, dto.newPassword);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
