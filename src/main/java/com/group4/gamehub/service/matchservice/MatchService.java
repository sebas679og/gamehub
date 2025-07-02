package com.group4.gamehub.service.matchservice;

import com.group4.gamehub.dto.requests.match.Match;
import com.group4.gamehub.dto.responses.match.Matchs;
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
   * @return a list of {@link com.group4.gamehub.dto.responses.match.Match} representing the generated matches
   */
  Matchs generateMatchesForTournament(UUID tournamentId);

  /**
   * Retrieves the details of a specific match by its ID.
   *
   * @param matchId the UUID of the match
   * @return a {@link com.group4.gamehub.dto.responses.match.Match} containing match information
   */
  com.group4.gamehub.dto.responses.match.Match getMatchById(UUID matchId);

  /**
   * Updates the result of a specific match.
   *
   * @param matchId the UUID of the match to update
   * @param match the request containing the new match result
   * @return a {@link com.group4.gamehub.dto.responses.match.Match} with the updated match information
   */
  com.group4.gamehub.dto.responses.match.Match updateMatchResult(UUID matchId, Match match);
}
