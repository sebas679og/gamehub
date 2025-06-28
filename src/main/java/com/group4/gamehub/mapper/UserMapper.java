package com.group4.gamehub.mapper;

import com.group4.gamehub.dto.responses.PublicUserResponse;
import com.group4.gamehub.dto.responses.UserResponse;
import com.group4.gamehub.model.UserEntity;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting {@link UserEntity} objects into response DTOs. Implemented
 * automatically by MapStruct at build time.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

  /**
   * Maps a {@link UserEntity} to a {@link UserResponse}, including sensitive fields like email.
   *
   * @param entity the user entity to map
   * @return the mapped {@link UserResponse} DTO
   */
  UserResponse toUserResponse(UserEntity entity);

  /**
   * Maps a {@link UserEntity} to a {@link PublicUserResponse}, excluding sensitive fields.
   *
   * @param entity the user entity to map
   * @return the mapped {@link PublicUserResponse} DTO
   */
  PublicUserResponse toPublicUserResponse(UserEntity entity);
}
