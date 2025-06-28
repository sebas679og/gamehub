package com.group4.gamehub.service.authservice;

import com.group4.gamehub.dto.requests.LoginRequest;
import com.group4.gamehub.dto.requests.RegisterRequest;
import com.group4.gamehub.dto.responses.AuthResponse;

/** Interface defining authentication operations such as user registration and login. */
public interface AuthServiceInterface {

  /**
   * Registers a new user based on the provided registration request.
   *
   * @param request the registration request containing username, email, and password
   * @return an {@link AuthResponse} containing a JWT token for the newly created user
   */
  AuthResponse register(RegisterRequest request);

  /**
   * Authenticates a user based on the provided login request.
   *
   * @param request the login request containing username and password
   * @return an {@link AuthResponse} containing a JWT token if authentication is successful
   */
  AuthResponse login(LoginRequest request);
}
