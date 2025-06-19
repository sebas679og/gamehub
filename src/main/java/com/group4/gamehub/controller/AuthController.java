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
import com.group4.gamehub.dto.responses.ErrorResponse;
import com.group4.gamehub.service.AuthService.AuthServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for authentication and registration")
public class AuthController {

    private final AuthServiceInterface authService;

    public AuthController(AuthServiceInterface authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(
        summary = "Register a new user",
        description = "Creates a new user with PLAYER role and returns JWT",
        responses = {
            @ApiResponse(responseCode = "201", description = "User registered",
                content=@Content(mediaType = "application/json",
                schema=@Schema(implementation=AuthResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflict, registered user",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
                    ))
        }
    )
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(
        summary = "Login with username and password",
        description = "Returns a JWT token if credentials are valid",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful login",
                content=@Content(mediaType = "application/json",
                schema=@Schema(implementation=AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, Incorrect credentials",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
                    ))
        }
    )
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
