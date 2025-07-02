package com.group4.gamehub.service.matchservice;

import com.group4.gamehub.dto.requests.match.MatchRequest;
import com.group4.gamehub.dto.responses.match.MatchResponse;
import com.group4.gamehub.dto.responses.match.MatchsResponse;
import java.util.UUID;

/**
 * Service interface for handling tournament match operations.
 *
 * <p>Provides methods to generate matches, retrieve match details, and update match results.
 */
public interface MatchService {

  /**
   * Generates player pairings (matches) for a given tournament.
   *
   * @param tournamentId the UUID of the tournament
   * @return a list of {@link MatchResponse} representing the generated matches
   */
  MatchsResponse generateMatchesForTournament(UUID tournamentId);

  /**
   * Retrieves the details of a specific match by its ID.
   *
   * @param matchId the UUID of the match
   * @return a {@link MatchResponse} containing match information
   */
  MatchResponse getMatchById(UUID matchId);

  /**
   * Updates the result of a specific match.
   *
   * @param matchId the UUID of the match to update
   * @param matchRequest the request containing the new match result
   * @return a {@link MatchResponse} with the updated match information
   */
  MatchResponse updateMatchResult(UUID matchId, MatchRequest matchRequest);
}
