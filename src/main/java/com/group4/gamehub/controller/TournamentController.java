package com.group4.gamehub.controller;

import com.group4.gamehub.dto.requests.tournament.TournamentsRequest;
import com.group4.gamehub.dto.responses.ErrorResponse;
import com.group4.gamehub.dto.responses.tournament.JoinTournament;
import com.group4.gamehub.dto.responses.tournament.TournamentDetails;
import com.group4.gamehub.dto.responses.tournament.TournamentsResponse;
import com.group4.gamehub.service.tournamentservice.TournamentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing tournaments.
 *
 * <p>Provides endpoints for creating tournaments, listing available tournaments, retrieving
 * tournament details, and allowing players to join tournaments.
 */
@RestController
@RequestMapping("/api/tournaments")
@Tag(
    name = "Tournament management",
    description =
        "Endpoints for managing tournaments, including creating tournaments, joining tournaments, and retrieving tournament details.")
public class TournamentController {

  private static final String APPLICATION_JSON = MediaType.APPLICATION_JSON_VALUE;
  private static final String BAD_REQUEST_CODE = "400";
  private static final String BAD_REQUEST_DESC = "Bad Request - Exception of input validations";
  private static final String UNAUTHORIZED_CODE = "401";
  private static final String UNAUTHORIZED_DESC = "Unauthorized – token is missing or invalid.";

  private final TournamentService tournamentService;

  /**
   * Constructs a {@code TournamentController} with the given service dependency.
   *
   * @param tournamentService the tournament service to be used for handling operations
   */
  public TournamentController(TournamentService tournamentService) {
    this.tournamentService = tournamentService;
  }

  /**
   * Creates one or more tournaments based on the provided request. Only accessible to users with
   * the ADMIN role.
   *
   * @param request the tournaments creation request
   * @return the response containing created tournaments
   */
  @PostMapping("/")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(
      summary = "Generate tournament matchups",
      description =
          "Generation of tournaments from a list, each tournament must have its name and maximum number of players (default 2 if not passed).",
      requestBody =
          @RequestBody(
              content =
                  @Content(
                      mediaType = APPLICATION_JSON,
                      schema = @Schema(implementation = TournamentsRequest.class))),
      responses = {
        @ApiResponse(
            responseCode = "201",
            description = "Created - Successfully created tournaments",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = TournamentsResponse.class))),
        @ApiResponse(
            responseCode = BAD_REQUEST_CODE,
            description = BAD_REQUEST_DESC,
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = UNAUTHORIZED_CODE,
            description = UNAUTHORIZED_DESC,
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden – user is authenticated but does not have the required role.",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  public ResponseEntity<TournamentsResponse> createTournaments(TournamentsRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(tournamentService.createTournaments(request));
  }

  /**
   * Retrieves all tournaments with status {@code CREATED}.
   *
   * @return the list of available tournaments
   */
  @GetMapping("/")
  @Operation(
      summary = "Get all tournaments",
      description = "Retrieves a list of all tournaments where your status is equal to created.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Ok - Successfully retrieved the list of tournaments",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = TournamentsResponse.class))),
        @ApiResponse(
            responseCode = BAD_REQUEST_CODE,
            description = BAD_REQUEST_DESC,
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = UNAUTHORIZED_CODE,
            description = UNAUTHORIZED_DESC,
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  public ResponseEntity<TournamentsResponse> getTournaments() {
    return ResponseEntity.ok(tournamentService.getTournaments());
  }

  /**
   * Retrieves the details of a specific tournament using its slug identifier.
   *
   * @param slug the unique slug of the tournament
   * @return tournament details
   */
  @GetMapping("/{slug}")
  @Operation(
      summary = "Get tournament details",
      description = "Retrieves the details of a specific tournament by its slug.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Ok - Successfully retrieved tournament details",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = TournamentDetails.class))),
        @ApiResponse(
            responseCode = BAD_REQUEST_CODE,
            description = BAD_REQUEST_DESC,
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = UNAUTHORIZED_CODE,
            description = UNAUTHORIZED_DESC,
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not Found - Tournament with the specified slug was not found.",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class))),
      })
  public ResponseEntity<TournamentDetails> getTournamentDetails(String slug) {
    return ResponseEntity.ok(tournamentService.getTournamentDetails(slug));
  }

  /**
   * Allows an authenticated player to join a tournament, if the tournament is in CREATED state and
   * the player has not already joined.
   *
   * @param authentication the authentication object containing the user's identity
   * @param slug the slug of the tournament to join
   * @return a confirmation response with tournament participation info
   */
  @PostMapping("/{slug}/join")
  @PreAuthorize("hasAnyRole('PLAYER')")
  @Operation(
      summary = "Join a tournament",
      description =
          "Allows a player to join a tournament by its slug. The tournament must be in the 'created' status and the player must not have already joined.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Ok - Successfully joined the tournament",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = JoinTournament.class))),
        @ApiResponse(
            responseCode = BAD_REQUEST_CODE,
            description = BAD_REQUEST_DESC,
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = UNAUTHORIZED_CODE,
            description = UNAUTHORIZED_DESC,
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden – user is authenticated but does not have the required role.",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description =
                "Not Found - The tournament with the specified slug was not found | User not found.",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  public ResponseEntity<JoinTournament> joinTournament(Authentication authentication, String slug) {
    return ResponseEntity.ok(tournamentService.joinTournament(authentication.getName(), slug));
  }
}
