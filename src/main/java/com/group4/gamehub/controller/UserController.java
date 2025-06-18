package com.group4.gamehub.controller;

import com.group4.gamehub.dto.responses.PublicUserResponse;
import com.group4.gamehub.dto.responses.UserResponse;
import com.group4.gamehub.service.UserService.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('PLAYER', 'ADMIN')")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        return ResponseEntity.ok(userService.findByUsername(authentication.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicUserResponse> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }
}
