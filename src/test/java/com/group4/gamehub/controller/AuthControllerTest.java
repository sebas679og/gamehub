package com.group4.gamehub.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.group4.gamehub.dto.requests.LoginRequest;
import com.group4.gamehub.dto.requests.RegisterRequest;
import com.group4.gamehub.dto.responses.AuthResponse;
import com.group4.gamehub.service.authservice.AuthServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class AuthControllerTest {

  @Mock private AuthServiceInterface authService;

  @InjectMocks private AuthController authController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void register_ReturnsCreatedAndAuthResponse() {
    RegisterRequest request =
        RegisterRequest.builder()
            .username("testusername")
            .email("test@username.com")
            .password("password")
            .build();
    AuthResponse expectedResponse = new AuthResponse("test-token");
    when(authService.register(ArgumentMatchers.any(RegisterRequest.class)))
        .thenReturn(expectedResponse);

    ResponseEntity<AuthResponse> response = authController.register(request);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(expectedResponse, response.getBody());
    verify(authService, times(1)).register(request);
  }

  @Test
  void login_ReturnsOkAndAuthResponse() {
    LoginRequest request =
        LoginRequest.builder().username("testusername").password("password").build();
    AuthResponse expectedResponse = new AuthResponse("test-token");
    when(authService.login(ArgumentMatchers.any(LoginRequest.class))).thenReturn(expectedResponse);

    ResponseEntity<AuthResponse> response = authController.login(request);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expectedResponse, response.getBody());
    verify(authService, times(1)).login(request);
  }
}
