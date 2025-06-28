package com.group4.gamehub.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.group4.gamehub.model.UserEntity;
import com.group4.gamehub.repository.UserRepository;
import com.group4.gamehub.util.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AdminUserInitializerTest {

  private AdminProperties adminProperties;
  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;
  private AdminUserInitializer initializer;

  @BeforeEach
  void setUp() {
    adminProperties = mock(AdminProperties.class);
    userRepository = mock(UserRepository.class);
    passwordEncoder = mock(PasswordEncoder.class);
    initializer = new AdminUserInitializer(adminProperties, userRepository, passwordEncoder);
  }

  @Test
  void shouldCreateAdminUserIfNotExists() throws Exception {
    when(adminProperties.getUsername()).thenReturn("admin");
    when(adminProperties.getPassword()).thenReturn("secret");
    when(adminProperties.getEmail()).thenReturn("admin@example.com");
    when(userRepository.existsByUsername("admin")).thenReturn(false);
    when(passwordEncoder.encode("secret")).thenReturn("encodedPassword");

    initializer.run();

    ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
    verify(userRepository).save(userCaptor.capture());

    UserEntity savedUser = userCaptor.getValue();
    assertEquals("admin", savedUser.getUsername());
    assertEquals("encodedPassword", savedUser.getPassword());
    assertEquals("admin@example.com", savedUser.getEmail());
    assertEquals(Role.ADMIN, savedUser.getRole());
  }

  @Test
  void shouldNotCreateAdminUserIfAlreadyExists() throws Exception {
    when(adminProperties.getUsername()).thenReturn("admin");
    when(userRepository.existsByUsername("admin")).thenReturn(true);

    initializer.run();

    verify(userRepository, never()).save(any());
  }
}
