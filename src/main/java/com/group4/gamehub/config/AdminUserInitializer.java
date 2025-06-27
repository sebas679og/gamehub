package com.group4.gamehub.config;

import com.group4.gamehub.model.UserEntity;
import com.group4.gamehub.repository.UserRepository;
import com.group4.gamehub.util.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Initializes a default admin user at application startup if it does not already exist.
 *
 * <p>This component runs once when the application starts and checks if an admin user is already
 * present. If not, it creates one using the credentials defined in {@link AdminProperties}.
 */
@Component
public class AdminUserInitializer implements CommandLineRunner {

  private final AdminProperties adminProperties;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * Constructs the initializer with required dependencies.
   *
   * @param adminProperties the admin user credentials and configuration
   * @param userRepository the repository used to persist the user
   * @param passwordEncoder encoder for hashing the password before saving
   */
  public AdminUserInitializer(
      AdminProperties adminProperties,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder) {
    this.adminProperties = adminProperties;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Executes on application startup. Creates a default admin user if one does not already exist by
   * username.
   *
   * @param args application startup arguments
   * @throws Exception if an error occurs during user creation
   */
  @Override
  public void run(String... args) throws Exception {
    if (!userRepository.existsByUsername(adminProperties.getUsername())) {
      UserEntity userEntity =
          UserEntity.builder()
              .username(adminProperties.getUsername())
              .password(passwordEncoder.encode(adminProperties.getPassword()))
              .email(adminProperties.getEmail())
              .role(Role.ADMIN)
              .build();

      userRepository.save(userEntity);
    }
  }
}
