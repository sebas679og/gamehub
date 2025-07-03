package com.group4.gamehub.dto.responses.tournament;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO representing a list of basic tournament data.
 *
 * <p>Typically used in responses where multiple tournaments are returned, such as in listing
 * endpoints.
 */
@Builder
@Setter
@Getter
public class TournamentsResponse {

  /** List of tournaments containing basic information like name, slug, and status. */
  private List<TournamentBasic> tournaments;
}
