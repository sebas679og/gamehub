package com.group4.gamehub.repository;

import com.group4.gamehub.model.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link UserEntity}. Provides CRUD operations and custom query methods
 * for managing user data.
 */
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

  /**
   * Finds a user by their email address.
   *
   * @param email the user's email address.
   * @return an {@link Optional} containing the user if found, or empty otherwise.
   */
  Optional<UserEntity> findByEmail(String email);

  /**
   * Finds a user by their username.
   *
   * @param username the user's username.
   * @return an {@link Optional} containing the user if found, or empty otherwise.
   */
  Optional<UserEntity> findByUsername(String username);

  /**
   * Checks if a user with the given email address exists.
   *
   * @param email the email address to check.
   * @return {@code true} if a user with the email exists, {@code false} otherwise.
   */
  boolean existsByEmail(String email);

  /**
   * Checks if a user with the given username exists.
   *
   * @param username the username to check.
   * @return {@code true} if a user with the username exists, {@code false} otherwise.
   */
  boolean existsByUsername(String username);
}
