package com.group4.gamehub.controller;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group4.gamehub.dto.responses.PublicUserResponse;
import com.group4.gamehub.dto.responses.UserResponse;
import com.group4.gamehub.exception.UserNotFoundException;
import com.group4.gamehub.model.UserEntity;
import com.group4.gamehub.repository.UserRepository;

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
                .orElseThrow(() -> new UserNotFoundException("User not found"));

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
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return PublicUserResponse.builder()
                .username(userEntity.getUsername())
                .role(userEntity.getRole())
                .rank(userEntity.getRank())
                .points(userEntity.getPoints())
                .build();
    }
}
