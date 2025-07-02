package com.group4.gamehub.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.group4.gamehub.dto.requests.auth.Login;
import com.group4.gamehub.dto.requests.auth.Register;
import com.group4.gamehub.dto.responses.auth.AuthResponse;
import com.group4.gamehub.service.authservice.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class AuthControllerTest {

  @Mock private AuthService authService;

  @InjectMocks private AuthController authController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void register_ReturnsCreatedAndAuthResponse() {
    Register request =
        Register.builder()
            .username("testusername")
            .email("test@username.com")
            .password("password")
            .build();
    AuthResponse expectedResponse = new AuthResponse("test-token");
    when(authService.register(ArgumentMatchers.any(Register.class)))
        .thenReturn(expectedResponse);

    ResponseEntity<AuthResponse> response = authController.register(request);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(expectedResponse, response.getBody());
    verify(authService, times(1)).register(request);
  }

  @Test
  void login_ReturnsOkAndAuthResponse() {
    Login request =
        Login.builder().username("testusername").password("password").build();
    AuthResponse expectedResponse = new AuthResponse("test-token");
    when(authService.login(ArgumentMatchers.any(Login.class))).thenReturn(expectedResponse);

    ResponseEntity<AuthResponse> response = authController.login(request);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expectedResponse, response.getBody());
    verify(authService, times(1)).login(request);
  }
}
