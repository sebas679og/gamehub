package com.group4.gamehub.controller;

import com.group4.gamehub.dto.UserResponse;
import com.group4.gamehub.model.User;
import com.group4.gamehub.repository.UserRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/me")
    @PreAuthorize("hasAnyRole('PLAYER', 'ADMIN')")
    public UserResponse getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByUsername(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .rank(user.getRank())
                .points(user.getPoints())
                .build();
    }
}
