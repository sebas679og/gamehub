package com.group4.gamehub.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.group4.gamehub.util.Role;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

class JwtServiceTest {

  private JwtService jwtService;

  @BeforeEach
  void setUp() {
    JwtProperties jwtProperties = new JwtProperties();
    jwtProperties.setSecret("test-secret-key");
    jwtProperties.setExpiration(1000 * 60 * 60); // 1 hora
    jwtService = new JwtService(jwtProperties);
  }

  @Test
  void generateToken_AndExtractUsername_ReturnsCorrectUsername() {
    UserDetails userDetails = mock(UserDetails.class);
    when(userDetails.getUsername()).thenReturn("testuser");
    when(userDetails.getAuthorities())
        .thenReturn((List) List.of(new SimpleGrantedAuthority(Role.PLAYER.name())));

    String token = jwtService.generateToken(userDetails);

    assertNotNull(token);
    String extractedUsername = jwtService.extractUsername(token);
    assertEquals("testuser", extractedUsername);
  }

  @Test
  void isTokenValid_WithCorrectUser_ReturnsTrue() {
    UserDetails userDetails = mock(UserDetails.class);
    when(userDetails.getUsername()).thenReturn("validuser");
    when(userDetails.getAuthorities())
        .thenReturn((List) List.of(new SimpleGrantedAuthority(Role.ADMIN.name())));

    String token = jwtService.generateToken(userDetails);
    when(userDetails.getUsername()).thenReturn("validuser"); // Refresca mock

    boolean isValid = jwtService.isTokenValid(token, userDetails);
    assertTrue(isValid);
  }

  @Test
  void isTokenValid_WithWrongUsername_ReturnsFalse() {
    UserDetails tokenUser = mock(UserDetails.class);
    when(tokenUser.getUsername()).thenReturn("correctuser");
    when(tokenUser.getAuthorities())
        .thenReturn((List) List.of(new SimpleGrantedAuthority(Role.PLAYER.name())));

    String token = jwtService.generateToken(tokenUser);

    UserDetails anotherUser = mock(UserDetails.class);
    when(anotherUser.getUsername()).thenReturn("otheruser");

    boolean isValid = jwtService.isTokenValid(token, anotherUser);
    assertFalse(isValid);
  }

  @Test
  void extractUsername_ReturnsExpectedValue() {
    UserDetails userDetails = mock(UserDetails.class);
    when(userDetails.getUsername()).thenReturn("extractedUser");
    when(userDetails.getAuthorities())
        .thenReturn((List) List.of(new SimpleGrantedAuthority(Role.ADMIN.name())));

    String token = jwtService.generateToken(userDetails);
    String username = jwtService.extractUsername(token);

    assertEquals("extractedUser", username);
  }
}
