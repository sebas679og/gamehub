package com.group4.gamehub.service.userservice;

import com.group4.gamehub.dto.responses.user.PublicUser;
import com.group4.gamehub.dto.responses.user.User;
import com.group4.gamehub.exception.NotFoundException;
import com.group4.gamehub.mapper.UserMapper;
import com.group4.gamehub.model.UserEntity;
import com.group4.gamehub.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link UserServiceInterface}. Provides user-related operations such as
 * retrieval by email, username, or ID.
 */
@Service
public class UserServiceImpl implements UserServiceInterface {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  /**
   * Constructs the user service with required dependencies.
   *
   * @param userRepository the repository for accessing user data
   * @param userMapper the mapper for converting {@link UserEntity} to DTOs
   */
  public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  /**
   * Retrieves a user by their email address.
   *
   * @param email the user's email
   * @return an {@link Optional} containing the user if found, or empty if not
   */
  @Override
  public Optional<UserEntity> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  /**
   * Retrieves full user details by username.
   *
   * @param username the user's username
   * @return a {@link User} containing the user's information
   * @throws NotFoundException if no user is found with the given username
   */
  @Override
  public User findByUsername(String username) {
    UserEntity userEntity =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new NotFoundException("User not found"));
    return userMapper.toUserResponse(userEntity);
  }

  /**
   * Retrieves public user details by user ID.
   *
   * @param id the user's unique identifier
   * @return a {@link PublicUser} containing non-sensitive user information
   * @throws NotFoundException if no user is found with the given ID
   */
  @Override
  public PublicUser findById(UUID id) {
    UserEntity userEntity =
        userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    return userMapper.toPublicUserResponse(userEntity);
  }
}
