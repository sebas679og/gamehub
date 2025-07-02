package com.group4.gamehub.dto.requests.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for user registration requests. Contains the necessary fields to
 * create a new user account.
 */
@Getter
@Setter
@Builder
public class Register {

  /** Desired username for the new user. Must not be blank. */
  @NotBlank(message = "Username must not be blank")
  private String username;

  /** Email address for the new user. Must be valid and not blank. */
  @NotBlank(message = "Email must not be blank")
  @Email(message = "Email must be valid")
  private String email;

  /** Password for the new user account. Must not be blank. */
  @NotBlank(message = "Password must not be blank")
  private String password;
}
