package com.group4.gamehub.dto.responses.tournament;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO representing a tournament along with its maximum number of players.
 *
 * <p>Extends {@link TournamentBasic} to include the player capacity detail.
 */
@Getter
@Setter
@SuperBuilder
public class TournamentPlayers extends TournamentBasic {

  /** The maximum number of players allowed in the tournament. */
  @JsonProperty(value = "max_players")
  private int maxPlayers;
}
