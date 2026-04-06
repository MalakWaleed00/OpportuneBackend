package com.example.demo.common.util;

import com.example.demo.repository.IUserRepository;
import com.example.demo.southbound.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtilsImpl implements IAuthUtils {

    private final IUserRepository userRepository;

    @Override
    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        String principal = authentication.getName();

        return userRepository.findByEmailOrUsername(principal, principal)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    @Override
    public String getCurrentUsername() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new RuntimeException("No authentication found");
        }

        return authentication.getName();
    }
}
