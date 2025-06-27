package com.group4.gamehub.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for login requests. Contains the user's credentials required for
 * authentication.
 */
@Getter
@Setter
@Builder
public class LoginRequest {

  /** The user's username. Must not be blank. */
  @NotBlank(message = "Username must not be blank")
  private String username;

  /** The user's password. Must not be blank. */
  @NotBlank(message = "Password must not be blank")
  private String password;
}
