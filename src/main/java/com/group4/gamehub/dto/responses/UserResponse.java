package com.group4.gamehub.dto.responses;

import com.group4.gamehub.util.Role;
import lombok.Builder;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) for returning full user details. Typically used in authenticated
 * endpoints or admin views.
 */
@Getter
@Builder
public class UserResponse {

  /** The user's username. */
  private String username;

  /** The user's email address. */
  private String email;

  /** The user's assigned role (e.g., USER, ADMIN). */
  private Role role;

  /** The user's current rank or title. */
  private String rank;

  /** The total number of points the user has accumulated. */
  private Long points;
}
