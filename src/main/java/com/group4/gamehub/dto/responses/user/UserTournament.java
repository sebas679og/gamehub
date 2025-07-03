package com.group4.gamehub.dto.responses.user;

import com.group4.gamehub.util.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO representing a user's public information within a tournament context.
 *
 * <p>Contains the username and their role (e.g., PLAYER, ADMIN).
 */
@Builder
@Setter
@Getter
public class UserTournament {

  /** The username of the participant. */
  private String username;

  /** The role of the user in the system (e.g., PLAYER, ADMIN). */
  private Role role;
}
