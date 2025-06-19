package com.group4.gamehub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group4.gamehub.dto.requests.LoginRequest;
import com.group4.gamehub.dto.requests.RegisterRequest;
import com.group4.gamehub.dto.responses.AuthResponse;
import com.group4.gamehub.service.AuthService.AuthServiceInterface;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthServiceInterface authService;

    public AuthController(AuthServiceInterface authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
