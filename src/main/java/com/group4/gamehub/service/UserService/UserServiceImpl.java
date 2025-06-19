package com.group4.gamehub.service.UserService;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.group4.gamehub.dto.responses.PublicUserResponse;
import com.group4.gamehub.dto.responses.UserResponse;
import com.group4.gamehub.exception.UserNotFoundException;
import com.group4.gamehub.mapper.UserMapper;
import com.group4.gamehub.model.UserEntity;
import com.group4.gamehub.repository.UserRepository;

@Service
public class UserServiceImpl implements UserServiceInterface {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserResponse findByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toUserResponse(userEntity);
    }

    @Override
    public PublicUserResponse findById(UUID id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toPublicUserResponse(userEntity);
    }
}
