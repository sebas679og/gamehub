package com.group4.gamehub.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) for authentication responses. Contains the JWT token issued after a
 * successful login.
 */
@Getter
@AllArgsConstructor
public class AuthResponse {

  /** JWT token assigned to the authenticated user. */
  private String token;
}
