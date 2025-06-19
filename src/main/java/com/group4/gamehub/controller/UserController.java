package com.group4.gamehub.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group4.gamehub.dto.responses.ErrorResponse;
import com.group4.gamehub.dto.responses.PublicUserResponse;
import com.group4.gamehub.dto.responses.UserResponse;
import com.group4.gamehub.service.UserService.UserServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Endpoints for user data access")
public class UserController {

    private final UserServiceInterface userService;

    public UserController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @Operation(
        summary = "Get current user info",
        description = "Returns information about the currently authenticated user",
        responses = {
            @ApiResponse(responseCode = "200", description = "Current user info",
                content=@Content(mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, User not found",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @ApiResponse(responseCode = "409", description = "Conflict",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
                    ))
        }
    )
    @PreAuthorize("hasAnyRole('PLAYER', 'ADMIN')")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        return ResponseEntity.ok(userService.findByUsername(authentication.getName()));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get public info of a user by ID",
        description = "Returns public profile info of a user",
        responses = {
            @ApiResponse(responseCode = "200", description = "User info found",
                content=@Content(mediaType = "application/json",
                schema = @Schema(implementation = PublicUserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, User not found",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @ApiResponse(responseCode = "409", description = "Conflict, Expired or corrupted token",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
                    ))
        }
    )
    public ResponseEntity<PublicUserResponse> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }
}
