package com.group4.gamehub.config;

import com.group4.gamehub.model.UserEntity;
import com.group4.gamehub.repository.UserRepository;
import com.group4.gamehub.util.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements CommandLineRunner {

  private final AdminProperties adminProperties;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AdminUserInitializer(
      AdminProperties adminProperties,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder) {
    this.adminProperties = adminProperties;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

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
