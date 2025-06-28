package com.group4.gamehub.model;

import com.group4.gamehub.util.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a user in the system. Contains authentication and profile-related
 * information.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class UserEntity {

  /** Unique identifier for the user. */
  @Id @GeneratedValue private UUID id;

  /** Unique username used for login and identification. */
  @Column(unique = true, nullable = false)
  private String username;

  /** Unique email address associated with the user. */
  @Column(unique = true, nullable = false)
  private String email;

  /** Encrypted password for authentication. */
  @Column(nullable = false)
  private String password;

  /** Role assigned to the user (e.g., ADMIN, USER). */
  @Enumerated(EnumType.STRING)
  private Role role;

  /** User's rank or title within the platform (optional). */
  @Column(name = "ranking")
  private String rank;

  /** Total points accumulated by the user (e.g., based on performance or participation). */
  private Long points;
}
