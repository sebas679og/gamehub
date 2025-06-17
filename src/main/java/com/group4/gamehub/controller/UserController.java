package com.group4.gamehub.controller;

import com.group4.gamehub.dto.PublicUserResponse;
import com.group4.gamehub.dto.UserResponse;
import com.group4.gamehub.model.UserEntity;
import com.group4.gamehub.repository.UserRepository;

import java.util.UUID;

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

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('PLAYER', 'ADMIN')")
    public UserResponse getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        UserEntity userEntity = userRepository.findByUsername(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return UserResponse.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .rank(userEntity.getRank())
                .points(userEntity.getPoints())
                .build();
    }

    @GetMapping("/{id}")
    public PublicUserResponse getUserById(@PathVariable UUID id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return PublicUserResponse.builder()
                .username(userEntity.getUsername())
                .role(userEntity.getRole())
                .rank(userEntity.getRank())
                .points(userEntity.getPoints())
                .build();
    }
}
