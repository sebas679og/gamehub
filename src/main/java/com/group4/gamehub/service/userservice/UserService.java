package com.group4.gamehub.service.userservice;

import com.group4.gamehub.dto.responses.user.PublicUser;
import com.group4.gamehub.dto.responses.user.User;
import com.group4.gamehub.exception.NotFoundException;
import com.group4.gamehub.model.UserEntity;
import java.util.Optional;
import java.util.UUID;

/** Interface for user-related operations such as retrieval by email, username, or ID. */
public interface UserService {

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
   * @return a {@link User} containing detailed user data
   * @throws NotFoundException if no user is found with the given username
   */
  User findByUsername(String username);

  /**
   * Finds a user by their ID and returns public (non-sensitive) user information.
   *
   * @param username the UUID of the user to retrieve
   * @return a {@link PublicUser} with limited user data
   * @throws NotFoundException if no user is found with the given ID
   */
  PublicUser findByUsernamePublic(String username);
}
