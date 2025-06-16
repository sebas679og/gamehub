package com.group4.gamehub.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.group4.gamehub.config.JwtService;
import com.group4.gamehub.dto.AuthResponse;
import com.group4.gamehub.dto.RegisterRequest;
import com.group4.gamehub.model.Role;
import com.group4.gamehub.model.User;
import com.group4.gamehub.repository.UserRepository;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email ya registrado");
        }
        if(userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username ya registrado");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.PLAYER)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(
            org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole().name())
                    .build()
        );

        return new AuthResponse(token);
    }
}
