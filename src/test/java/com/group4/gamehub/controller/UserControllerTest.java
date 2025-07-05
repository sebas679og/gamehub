package com.group4.gamehub.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.group4.gamehub.dto.responses.user.PublicUser;
import com.group4.gamehub.dto.responses.user.User;
import com.group4.gamehub.service.userservice.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

class UserControllerTest {

  @Mock private UserService userService;

  @InjectMocks private UserController userController;

  @Mock private Authentication authentication;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getCurrentUser_ReturnsUserResponse() {
    String username = "testuser";
    User expectedResponse =
        User.builder()
            .username("testuser")
            .email("test@email.com")
            .rank("test-rank")
            .points(100L)
            .build();
    when(authentication.getName()).thenReturn(username);
    when(userService.findByUsername(username)).thenReturn(expectedResponse);

    ResponseEntity<User> response = userController.getCurrentUser(authentication);

    assertEquals(200, response.getStatusCode().value());
    assertEquals(expectedResponse, response.getBody());
    verify(userService, times(1)).findByUsername(username);
  }

  @Test
  void getUserById_ReturnsPublicUserResponse() {
    String testUsername = "testuser";
    PublicUser expectedResponse =
        PublicUser.builder().username(testUsername).rank("test-rank").points(100L).build();
    when(userService.findByUsernamePublic(testUsername)).thenReturn(expectedResponse);

    ResponseEntity<PublicUser> response = userController.getUserByUsername(testUsername);

    assertEquals(200, response.getStatusCode().value());
    assertEquals(expectedResponse, response.getBody());
    verify(userService, times(1)).findByUsernamePublic(testUsername);
  }
}
