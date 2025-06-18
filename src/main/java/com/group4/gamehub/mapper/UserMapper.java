package com.group4.gamehub.mapper;

import org.mapstruct.Mapper;

import com.group4.gamehub.dto.responses.PublicUserResponse;
import com.group4.gamehub.dto.responses.UserResponse;
import com.group4.gamehub.model.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(UserEntity entity);

    PublicUserResponse toPublicUserResponse(UserEntity entity);
}
