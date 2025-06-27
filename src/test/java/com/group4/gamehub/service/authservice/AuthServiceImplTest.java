package com.group4.gamehub.service.authservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.group4.gamehub.config.JwtService;
import com.group4.gamehub.dto.requests.LoginRequest;
import com.group4.gamehub.dto.requests.RegisterRequest;
import com.group4.gamehub.dto.responses.AuthResponse;
import com.group4.gamehub.exception.UserAlreadyExistsException;
import com.group4.gamehub.model.UserEntity;
import com.group4.gamehub.repository.UserRepository;
import com.group4.gamehub.util.Role;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthServiceImplTest {

  @Mock private UserRepository userRepository;
  @Mock private PasswordEncoder passwordEncoder;
  @Mock private JwtService jwtService;
  @Mock private AuthenticationManager authenticationManager;

  @InjectMocks private AuthServiceImpl authService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void register_Success_ReturnsAuthResponse() {
    RegisterRequest request =
        RegisterRequest.builder()
            .username("testuser")
            .email("test@email.com")
            .password("password")
            .build();

    when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
    when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
    when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword");
    when(jwtService.generateToken(any())).thenReturn("jwt-token");
    when(userRepository.save(any(UserEntity.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    AuthResponse response = authService.register(request);

    assertEquals("jwt-token", response.getToken());
    verify(userRepository).save(any(UserEntity.class));
  }

  @Test
  void register_ExistingEmail_ThrowsException() {
    RegisterRequest request =
        RegisterRequest.builder()
            .username("testuser")
            .email("test@email.com")
            .password("password")
            .build();

    when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

    assertThrows(UserAlreadyExistsException.class, () -> authService.register(request));
    verify(userRepository, never()).save(any());
  }

  @Test
  void register_ExistingUsername_ThrowsException() {
    RegisterRequest request =
        RegisterRequest.builder()
            .username("testuser")
            .email("test@email.com")
            .password("password")
            .build();

    when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
    when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);

    assertThrows(UserAlreadyExistsException.class, () -> authService.register(request));
    verify(userRepository, never()).save(any());
  }

  @Test
  void login_Success_ReturnsAuthResponse() {
    LoginRequest request = LoginRequest.builder().username("testuser").password("password").build();

    UserEntity user =
        UserEntity.builder()
            .username("testuser")
            .password("hashedPassword")
            .role(Role.PLAYER)
            .build();

    when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
    when(jwtService.generateToken(any())).thenReturn("jwt-token");

    Authentication mockAuthentication = Mockito.mock(Authentication.class);
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(mockAuthentication);

    AuthResponse response = authService.login(request);

    assertEquals("jwt-token", response.getToken());
    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(userRepository).findByUsername(request.getUsername());
  }

  @Test
  void login_UnregisteredUser_ThrowsException() {
    LoginRequest request = LoginRequest.builder().username("unknown").password("password").build();

    when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> authService.login(request));
  }
}
