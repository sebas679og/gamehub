package com.group4.gamehub.dto.responses.tournament;

import com.group4.gamehub.util.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Basic DTO representing minimal tournament information.
 *
 * <p>Typically used for lightweight responses or summaries where full details are unnecessary.
 */
@Getter
@Setter
@SuperBuilder
public class TournamentBasic {

  /** The unique slug identifier of the tournament. */
  private String slug;

  /** The display name of the tournament. */
  private String name;

  /** The current status of the tournament (e.g., CREATED, IN_PROGRESS, FINISHED). */
  private Status status;
}
