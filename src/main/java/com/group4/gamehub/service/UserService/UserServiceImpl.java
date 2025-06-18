package com.group4.gamehub.service.UserService;

import com.group4.gamehub.dto.responses.PublicUserResponse;
import com.group4.gamehub.dto.responses.UserResponse;
import com.group4.gamehub.exception.UserNotFoundException;
import com.group4.gamehub.mapper.UserMapper;
import com.group4.gamehub.model.UserEntity;
import com.group4.gamehub.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

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

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return null;
    }
}
