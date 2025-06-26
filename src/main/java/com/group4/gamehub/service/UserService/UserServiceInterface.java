package com.group4.gamehub.service.UserService;

import com.group4.gamehub.dto.responses.PublicUserResponse;
import com.group4.gamehub.dto.responses.UserResponse;
import com.group4.gamehub.model.UserEntity;
import java.util.Optional;
import java.util.UUID;

public interface UserServiceInterface {

  Optional<UserEntity> findByEmail(String email);

  UserResponse findByUsername(String username);

  PublicUserResponse findById(UUID id);
}
