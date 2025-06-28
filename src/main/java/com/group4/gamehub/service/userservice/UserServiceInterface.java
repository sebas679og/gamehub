package com.group4.gamehub.service.userservice;

import com.group4.gamehub.dto.responses.PublicUserResponse;
import com.group4.gamehub.dto.responses.UserResponse;
import com.group4.gamehub.model.UserEntity;
import java.util.Optional;
import java.util.UUID;

/** Interface for user-related operations such as retrieval by email, username, or ID. */
public interface UserServiceInterface {

  /**
   * Finds a user by their email address.
   *
   * @param email the email address to search for
   * @return an {@link Optional} containing the {@link UserEntity} if found, or empty otherwise
   */
  Optional<UserEntity> findByEmail(String email);

  /**
   * Finds a user by their username and returns full user information.
   *
   * @param username the username to search for
   * @return a {@link UserResponse} containing detailed user data
   * @throws UserNotFoundException if no user is found with the given username
   */
  UserResponse findByUsername(String username);

  /**
   * Finds a user by their ID and returns public (non-sensitive) user information.
   *
   * @param id the UUID of the user to retrieve
   * @return a {@link PublicUserResponse} with limited user data
   * @throws UserNotFoundException if no user is found with the given ID
   */
  PublicUserResponse findById(UUID id);
}
