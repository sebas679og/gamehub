package com.group4.gamehub.mapper;

import com.group4.gamehub.dto.responses.tournament.TournamentBasic;
import com.group4.gamehub.dto.responses.tournament.TournamentDetails;
import com.group4.gamehub.dto.responses.tournament.TournamentPlayers;
import com.group4.gamehub.model.TournamentEntity;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper interface for converting {@link TournamentEntity} instances into various DTO
 * representations used throughout the application.
 *
 * <p>Utilizes {@link MatchMapper} to map nested match-related objects when necessary.
 */
@Mapper(
    componentModel = "spring",
    uses = {MatchMapper.class})
public interface TournamentMapper {

  /**
   * Maps a {@link TournamentEntity} to a {@link TournamentPlayers} DTO.
   *
   * @param entity the tournament entity to map
   * @return a DTO containing the tournament and its associated players
   */
  TournamentPlayers toTournamentResponse(TournamentEntity entity);

  /**
   * Maps a {@link TournamentEntity} to a {@link TournamentBasic} DTO.
   *
   * @param entity the tournament entity to map
   * @return a simplified DTO representation of the tournament
   */
  TournamentBasic toTournamentBasicResponse(TournamentEntity entity);

  /**
   * Maps a {@link TournamentEntity} to a {@link TournamentDetails} DTO.
   *
   * @param entity the tournament entity to map
   * @return a detailed DTO representation of the tournament including its matches and users
   */
  TournamentDetails toTournamentDetailsResponse(TournamentEntity entity);
}
