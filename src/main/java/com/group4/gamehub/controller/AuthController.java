package com.group4.gamehub.controller;

import com.group4.gamehub.dto.requests.auth.Login;
import com.group4.gamehub.dto.requests.auth.Register;
import com.group4.gamehub.dto.responses.auth.AuthResponse;
import com.group4.gamehub.dto.responses.ErrorResponse;
import com.group4.gamehub.service.authservice.AuthServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing user authentication. Provides endpoints for user registration and
 * login.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {

  public static final String APPLICATION_JSON = MediaType.APPLICATION_JSON_VALUE;

  private final AuthServiceInterface authService;

  /**
   * Constructs the controller with the required authentication service.
   *
   * @param authService the authentication service implementation
   */
  public AuthController(AuthServiceInterface authService) {
    this.authService = authService;
  }

  /**
   * Registers a new user with the default PLAYER role.
   *
   * @param request the registration data
   * @return a {@link ResponseEntity} containing the JWT token and HTTP 201 status
   */
  @PostMapping("/register")
  @Operation(
      summary = "Register a new user",
      description = "Creates a new user with the PLAYER role and returns a JWT token.",
      responses = {
        @ApiResponse(
            responseCode = "201",
            description = "User registered successfully",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Conflict: user already exists",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  public ResponseEntity<AuthResponse> register(@Valid @RequestBody Register request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
  }

  /**
   * Authenticates a user and returns a JWT token if credentials are valid.
   *
   * @param request the login request containing username and password
   * @return a {@link ResponseEntity} containing the JWT token and HTTP 200 status
   */
  @PostMapping("/login")
  @Operation(
      summary = "Login with username and password",
      description = "Authenticates the user and returns a JWT token if credentials are correct.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful login",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request: invalid credentials",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody Login request) {
    return ResponseEntity.ok(authService.login(request));
  }
}
