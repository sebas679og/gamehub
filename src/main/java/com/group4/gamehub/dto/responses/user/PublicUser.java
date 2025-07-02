package com.group4.gamehub.dto.responses.user;

import com.group4.gamehub.util.Role;
import lombok.Builder;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) for exposing public user information. Intended for use in responses
 * where sensitive user data should be excluded.
 */
@Getter
@Builder
public class PublicUser {

  /** The user's username. */
  private String username;

  /** The user's assigned role (e.g., USER, ADMIN). */
  private Role role;

  /** The user's current rank or title. */
  private String rank;

  /** The total number of points the user has accumulated. */
  private Long points;
}
