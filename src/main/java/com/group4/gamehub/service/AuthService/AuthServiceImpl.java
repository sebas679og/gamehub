package com.group4.gamehub.service.AuthService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.group4.gamehub.config.JwtService;
import com.group4.gamehub.dto.requests.LoginRequest;
import com.group4.gamehub.dto.requests.RegisterRequest;
import com.group4.gamehub.dto.responses.AuthResponse;
import com.group4.gamehub.exception.UserAlreadyExistsException;
import com.group4.gamehub.model.UserEntity;
import com.group4.gamehub.repository.UserRepository;
import com.group4.gamehub.util.Role;

@Service
public class AuthServiceImpl implements AuthServiceInterface {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    private String generateJwtToken(UserEntity userEntity) {
        return jwtService.generateToken(
                org.springframework.security.core.userdetails.User.builder()
                        .username(userEntity.getUsername())
                        .password(userEntity.getPassword())
                        .roles(userEntity.getRole().name())
                        .build()
        );
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException("E-mail already registered");
        }
        if(userRepository.existsByUsername(request.getUsername())){
            throw new UserAlreadyExistsException("Username already registered");
        }

        UserEntity userEntity = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.PLAYER)
                .build();

        userRepository.save(userEntity);

        String token = generateJwtToken(userEntity);
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Unregistered user"));

        String token = generateJwtToken(user);

        return new AuthResponse(token);
    }
}
