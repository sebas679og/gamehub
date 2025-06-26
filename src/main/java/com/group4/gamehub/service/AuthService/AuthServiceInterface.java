package com.group4.gamehub.service.AuthService;

import com.group4.gamehub.dto.requests.LoginRequest;
import com.group4.gamehub.dto.requests.RegisterRequest;
import com.group4.gamehub.dto.responses.AuthResponse;

public interface AuthServiceInterface {
  AuthResponse register(RegisterRequest request);

  AuthResponse login(LoginRequest request);
}
