package com.group4.gamehub.controller;

import com.group4.gamehub.dto.responses.ErrorResponse;
import com.group4.gamehub.dto.responses.user.PublicUser;
import com.group4.gamehub.dto.responses.user.User;
import com.group4.gamehub.service.userservice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for managing user data and profiles. */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Endpoints for accessing user information")
public class UserController {

  public static final String APPLICATION_JSON = MediaType.APPLICATION_JSON_VALUE;

  private final UserService userService;

  /**
   * Constructs the controller with the required user service.
   *
   * @param userService the service for accessing user information
   */
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Returns the authenticated user's full profile information using the JWT token.
   *
   * @param authentication the current authentication context (injected automatically by Spring
   *     Security)
   * @return a {@link ResponseEntity} with {@link User} for the current user
   */
  @GetMapping("/me")
  @PreAuthorize("hasAnyRole('PLAYER', 'ADMIN')")
  @Operation(
      summary = "Get current user info",
      description = "Returns full information about the currently authenticated user.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "User info retrieved",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = User.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request: user not found",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized, token invalidated",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden – only administrators can update match results.",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  public ResponseEntity<User> getCurrentUser(Authentication authentication) {
    return ResponseEntity.ok(userService.findByUsername(authentication.getName()));
  }

  /**
   * Retrieves the public profile of a user by their UUID.
   *
   * @param id the UUID of the user to retrieve
   * @return a {@link ResponseEntity} with public user info
   */
  @GetMapping("/{id}")
  @Operation(
      summary = "Get public user info by ID",
      description = "Returns limited public profile information of a user by UUID.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "User found",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = PublicUser.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request: user not found",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized, token invalidated",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  public ResponseEntity<PublicUser> getUserById(@PathVariable UUID id) {
    return ResponseEntity.ok(userService.findById(id));
  }
}
