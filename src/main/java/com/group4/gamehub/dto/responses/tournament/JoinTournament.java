package com.group4.gamehub.dto.responses.tournament;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO representing the response after a user joins a tournament.
 *
 * <p>Includes a confirmation message and basic tournament and user details.
 */
@Getter
@Setter
@Builder
public class JoinTournament {

  /** A confirmation or status message regarding the join operation. */
  private String message;

  /** The unique slug identifier of the tournament. */
  @JsonProperty("tournament_slug")
  private String tournamentSlug;

  /** The display name of the tournament. */
  @JsonProperty("tournament_name")
  private String tournamentName;

  /** The username of the user who joined the tournament. */
  private String username;
}
