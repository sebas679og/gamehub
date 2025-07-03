package com.group4.gamehub.service.userservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.group4.gamehub.dto.responses.user.PublicUser;
import com.group4.gamehub.dto.responses.user.User;
import com.group4.gamehub.exception.NotFoundException;
import com.group4.gamehub.mapper.UserMapper;
import com.group4.gamehub.model.UserEntity;
import com.group4.gamehub.repository.UserRepository;
import com.group4.gamehub.util.Role;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserServiceImplTest {

  @Mock private UserRepository userRepository;
  @Mock private UserMapper userMapper;

  @InjectMocks private UserServiceImpl userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findByEmail_ExistingEmail_ReturnsUserEntity() {
    String email = "user@example.com";
    UserEntity user = UserEntity.builder().email(email).build();

    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

    Optional<UserEntity> result = userService.findByEmail(email);

    assertTrue(result.isPresent());
    assertEquals(email, result.get().getEmail());
    verify(userRepository).findByEmail(email);
  }

  @Test
  void findByUsername_ExistingUser_ReturnsUserResponse() {
    String username = "testuser";
    UserEntity user = UserEntity.builder().username(username).build();
    User response = User.builder().username(username).build();

    when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
    when(userMapper.toUserResponse(user)).thenReturn(response);

    User result = userService.findByUsername(username);

    assertEquals(username, result.getUsername());
    verify(userRepository).findByUsername(username);
    verify(userMapper).toUserResponse(user);
  }

  @Test
  void findByUsername_UserNotFound_ThrowsException() {
    String username = "unknown";

    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> userService.findByUsername(username));
    verify(userRepository).findByUsername(username);
    verify(userMapper, never()).toUserResponse(any());
  }

  @Test
  void findById_ExistingId_ReturnsPublicUserResponse() {
    UUID id = UUID.randomUUID();
    UserEntity user =
        UserEntity.builder()
            .username("publicUser")
            .role(Role.PLAYER)
            .rank("Silver")
            .points(100L)
            .build();

    PublicUser response =
        PublicUser.builder()
            .username("publicUser")
            .role(Role.PLAYER)
            .rank("Silver")
            .points(100L)
            .build();

    when(userRepository.findById(id)).thenReturn(Optional.of(user));
    when(userMapper.toPublicUserResponse(user)).thenReturn(response);

    PublicUser result = userService.findById(id);

    assertEquals("publicUser", result.getUsername());
    assertEquals(Role.PLAYER, result.getRole());
    assertEquals("Silver", result.getRank());
    assertEquals(100L, result.getPoints());
    verify(userRepository).findById(id);
    verify(userMapper).toPublicUserResponse(user);
  }

  @Test
  void findById_UserNotFound_ThrowsException() {
    UUID id = UUID.randomUUID();

    when(userRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> userService.findById(id));
    verify(userRepository).findById(id);
    verify(userMapper, never()).toPublicUserResponse(any());
  }
}
