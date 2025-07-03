package com.group4.gamehub.dto.requests.tournament;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO used to request the creation of multiple tournaments in a single request.
 *
 * <p>Each tournament in the list must include its name and optionally the maximum number of
 * players.
 */
@Getter
@Setter
@Builder
public class TournamentsRequest {

  /** List of tournament creation requests. Must not be null or blank. */
  @NotNull(message = "Tournaments list must not be null.")
  @Size(min = 1, message = "Tournaments list must contain at least one tournament.")
  private List<TournamentRequest> tournamentRequests;
}
