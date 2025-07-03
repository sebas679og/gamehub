package com.group4.gamehub.mapper;

import com.group4.gamehub.dto.responses.match.MatchResponse;
import com.group4.gamehub.model.MatchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting {@link MatchEntity} objects into {@link MatchResponse} DTOs.
 *
 * <p>This interface uses MapStruct to automatically generate the implementation at build time,
 * facilitating clean and decoupled data transformation logic.
 */
@Mapper(componentModel = "spring")
public interface MatchMapper {

  /**
   * Maps a {@link MatchEntity} to a {@link MatchResponse}. Extracts:
   *
   * <ul>
   *   <li>tournament name from the tournament entity.
   *   <li>player1 and player2 usernames from their respective user entities.
   *   <li>result from the match entity.
   * </ul>
   *
   * @param entity the MatchEntity to map
   * @return a Match DTO with mapped fields
   */
  @Mapping(source = "tournament.name", target = "tournamentName")
  @Mapping(source = "player1.username", target = "player1")
  @Mapping(source = "player2.username", target = "player2")
  @Mapping(source = "result", target = "result")
  MatchResponse toMatchResponse(MatchEntity entity);
}
