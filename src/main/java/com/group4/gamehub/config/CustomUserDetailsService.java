package com.group4.gamehub.config;

import com.group4.gamehub.model.UserEntity;
import com.group4.gamehub.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of {@link UserDetailsService} used by Spring Security to load user-specific
 * data during authentication.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  /**
   * Constructs the service with the required user repository.
   *
   * @param userRepository the repository to fetch user information from
   */
  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Loads a user by their username for authentication.
   *
   * @param username the username identifying the user
   * @return a {@link UserDetails} object containing the user's credentials and roles
   * @throws UsernameNotFoundException if the user does not exist
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userEntity =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return org.springframework.security.core.userdetails.User.builder()
        .username(userEntity.getUsername())
        .password(userEntity.getPassword())
        .roles(userEntity.getRole().name())
        .build();
  }
}
