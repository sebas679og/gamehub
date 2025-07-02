package com.group4.gamehub.service.matchservice;

import com.group4.gamehub.dto.requests.match.MatchRequest;
import com.group4.gamehub.dto.responses.match.MatchResponse;
import com.group4.gamehub.dto.responses.match.MatchsResponse;
import com.group4.gamehub.exception.NotFoundException;
import com.group4.gamehub.mapper.MatchMapper;
import com.group4.gamehub.model.MatchEntity;
import com.group4.gamehub.model.TournamentEntity;
import com.group4.gamehub.model.UserEntity;
import com.group4.gamehub.repository.MatchRepository;
import com.group4.gamehub.repository.TournamentRepository;
import com.group4.gamehub.util.Result;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link MatchService} that provides logic for:
 *
 * <ul>
 *   <li>Generating player matchups in tournaments
 *   <li>Retrieving match details
 *   <li>Updating match results
 * </ul>
 */
@Service
public class MatchServiceImpl implements MatchService {

  private final MatchRepository matchRepository;
  private final TournamentRepository tournamentRepository;
  private final MatchMapper matchMapper;

  public MatchServiceImpl(
      MatchRepository matchRepository,
      TournamentRepository tournamentRepository,
      MatchMapper matchMapper) {
    this.matchRepository = matchRepository;
    this.tournamentRepository = tournamentRepository;
    this.matchMapper = matchMapper;
  }

  /**
   * Retrieves a tournament by its ID or throws an exception if not found.
   *
   * @param tournamentId the UUID of the tournament
   * @return the {@link TournamentEntity}
   */
  private TournamentEntity getTournamentById(UUID tournamentId) {
    return tournamentRepository
        .findById(tournamentId)
        .orElseThrow(() -> new NotFoundException("Tournament not found"));
  }

  /**
   * Determines the next round number for a tournament.
   *
   * @param tournamentId the tournament UUID
   * @return the next round number as an integer
   */
  private int getNextRoundNumber(UUID tournamentId) {
    return matchRepository.findMaxRoundByTournamentId(tournamentId).orElse(0) + 1;
  }

  /** Minimum number of players required to generate matches in a tournament. */
  private static final int MIN_PLAYERS = 2;

  /**
   * Retrieves and shuffles valid players for the tournament. Ensures that at least two players are
   * registered.
   *
   * @param tournament the tournament entity
   * @return a shuffled list of players
   */
  private List<UserEntity> getValidPlayers(TournamentEntity tournament) {
    List<UserEntity> players = new ArrayList<>(tournament.getUserEntities());
    if (players.size() < MIN_PLAYERS) {
      throw new RuntimeException("Not enough players");
    }
    Collections.shuffle(players);
    return players;
  }

  /**
   * Creates and saves matches based on the provided players. If there is an odd number of players,
   * one match will have a null opponent.
   *
   * @param tournament the tournament entity
   * @param players the list of players
   * @param round the current round number
   * @return a list of saved matches
   */
  private List<MatchEntity> saveGeneratedMatches(
      TournamentEntity tournament, List<UserEntity> players, int round) {
    List<MatchEntity> matches = new ArrayList<>();

    for (int i = 0; i < players.size() - 1; i += 2) {
      matches.add(buildMatch(tournament, players.get(i), players.get(i + 1), round));
    }

    if (players.size() % 2 != 0) {
      matches.add(buildMatch(tournament, players.get(players.size() - 1), null, round));
    }

    return matchRepository.saveAll(matches);
  }

  /**
   * Builds a match entity between two players for a given round.
   *
   * @param tournament the tournament entity
   * @param player1 the first player
   * @param player2 the second player (can be null if unmatched)
   * @param round the round number
   * @return a new {@link MatchEntity}
   */
  private MatchEntity buildMatch(
      TournamentEntity tournament, UserEntity player1, UserEntity player2, int round) {
    return MatchEntity.builder()
        .tournament(tournament)
        .player1(player1)
        .player2(player2)
        .round(round)
        .result(Result.PENDING)
        .build();
  }

  /** {@inheritDoc} */
  @Override
  public MatchsResponse generateMatchesForTournament(UUID tournamentId) {
    TournamentEntity tournament = getTournamentById(tournamentId);
    List<UserEntity> players = getValidPlayers(tournament);
    int nextRound = getNextRoundNumber(tournamentId);
    List<MatchEntity> matches = saveGeneratedMatches(tournament, players, nextRound);

    List<MatchResponse> matchResponseRespons = matches.stream()
            .map(matchMapper::toMatchResponse)
            .toList();

    return MatchsResponse.builder()
            .matchResponses(matchResponseRespons)
            .build();
  }

  /** {@inheritDoc} */
  @Override
  public MatchResponse getMatchById(UUID matchId) {
    return matchMapper.toMatchResponse(
        matchRepository
            .findById(matchId)
            .orElseThrow(() -> new NotFoundException("Match not found")));
  }

  /** {@inheritDoc} */
  @Override
  public MatchResponse updateMatchResult(UUID matchId, MatchRequest matchRequest) {
    MatchEntity match =
        matchRepository
            .findById(matchId)
            .map(
                existingMatch -> {
                  if (existingMatch.getResult() != Result.PENDING) {
                    throw new RuntimeException("Match result already set");
                  }
                  return existingMatch;
                })
            .orElseThrow(() -> new NotFoundException("Match not found"));

    match.setResult(matchRequest.getResult());
    MatchEntity updated = matchRepository.save(match);
    return matchMapper.toMatchResponse(updated);
  }
}
