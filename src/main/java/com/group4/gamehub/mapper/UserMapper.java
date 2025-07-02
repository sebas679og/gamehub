package com.group4.gamehub.mapper;

import com.group4.gamehub.dto.responses.user.PublicUser;
import com.group4.gamehub.dto.responses.user.User;
import com.group4.gamehub.model.UserEntity;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting {@link UserEntity} objects into response DTOs. Implemented
 * automatically by MapStruct at build time.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

  /**
   * Maps a {@link UserEntity} to a {@link User}, including sensitive fields like email.
   *
   * @param entity the user entity to map
   * @return the mapped {@link User} DTO
   */
  User toUserResponse(UserEntity entity);

  /**
   * Maps a {@link UserEntity} to a {@link PublicUser}, excluding sensitive fields.
   *
   * @param entity the user entity to map
   * @return the mapped {@link PublicUser} DTO
   */
  PublicUser toPublicUserResponse(UserEntity entity);
}
