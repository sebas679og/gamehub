package com.group4.gamehub.service.tournamentservice;

import com.group4.gamehub.dto.requests.tournament.TournamentsRequest;
import com.group4.gamehub.dto.responses.tournament.JoinTournament;
import com.group4.gamehub.dto.responses.tournament.TournamentDetails;
import com.group4.gamehub.dto.responses.tournament.TournamentsResponse;

/**
 * Service interface for handling tournament-related operations such as creation, retrieval, and
 * user participation.
 */
public interface TournamentService {

  /**
   * Creates one or more tournaments based on the incoming request.
   *
   * @param request the request containing tournament creation data
   * @return a response containing the list of created tournaments
   */
  TournamentsResponse createTournaments(TournamentsRequest request);

  /**
   * Retrieves all tournaments that are currently in the CREATED status.
   *
   * @return a response containing the list of available tournaments
   */
  TournamentsResponse getTournaments();

  /**
   * Retrieves the details of a specific tournament using its slug.
   *
   * @param slug the unique identifier (slug) of the tournament
   * @return detailed information about the specified tournament
   */
  TournamentDetails getTournamentDetails(String slug);

  /**
   * Allows a user to join a tournament by its slug, if it is in CREATED status and the user has not
   * already joined.
   *
   * @param username the username of the player attempting to join
   * @param slug the slug of the tournament to join
   * @return information confirming the user's participation
   */
  JoinTournament joinTournament(String username, String slug);
}
