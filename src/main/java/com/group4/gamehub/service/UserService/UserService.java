package com.group4.gamehub.service.UserService;

import com.group4.gamehub.dto.responses.PublicUserResponse;
import com.group4.gamehub.dto.responses.UserResponse;
import com.group4.gamehub.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<UserEntity> findByEmail(String email);

    UserResponse findByUsername(String username);
    PublicUserResponse findById(UUID id);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
    Page<UserEntity> findAll(Pageable pageable);

}
