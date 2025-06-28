package com.group4.gamehub.controller;

import com.group4.gamehub.dto.requests.MatchRequest;
import com.group4.gamehub.dto.responses.ErrorResponse;
import com.group4.gamehub.dto.responses.MatchResponse;
import com.group4.gamehub.service.tournamentservice.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that handles tournament matchmaking operations and match result updates.
 *
 * <p>This controller exposes endpoints for:
 *
 * <ul>
 *   <li>Generating matchups for a tournament
 *   <li>Retrieving individual match details
 *   <li>Updating the result of a specific match
 * </ul>
 *
 * <p>Some endpoints are restricted to administrators via role-based authorization.
 */
@RestController
@RequestMapping("/api/match")
@Tag(
    name = "Live Matching",
    description =
        "Endpoints that handle player matchmaking and match result management in tournaments.")
public class MatchController {

  public static final String APPLICATION_JSON = MediaType.APPLICATION_JSON_VALUE;

  private final MatchService matchService;

  /**
   * Constructs the MatchController with the required {@link MatchService}.
   *
   * @param matchService service layer responsible for business logic of matches
   */
  public MatchController(MatchService matchService) {
    this.matchService = matchService;
  }

  /**
   * Generates player matchups for a specific tournament.
   *
   * <p>Accessible only to users with the ADMIN role.
   *
   * @param tournamentId the UUID of the tournament
   * @return a list of generated {@link MatchResponse} objects
   */
  @PostMapping("/generate/{tournamentId}")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(
      summary = "Generate tournament matchups",
      description =
          "Automatically generates player pairings for a given tournament. Restricted to administrators.",
      responses = {
        @ApiResponse(
            responseCode = "201",
            description =
                "Created - Successfully generated and returned the list of paired players.",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = MatchResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not Found - Tournament with the specified ID was not found.",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized – token is missing or invalid.",
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
  public ResponseEntity<List<MatchResponse>> generateMatches(@PathVariable UUID tournamentId) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(matchService.generateMatchesForTournament(tournamentId));
  }

  /**
   * Retrieves a specific match by its unique ID.
   *
   * @param matchId the UUID of the match
   * @return the corresponding {@link MatchResponse}
   */
  @GetMapping("/{matchId}")
  @Operation(
      summary = "Get match by ID",
      description = "Retrieves the details of a specific match using its unique ID.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Ok - Successfully retrieved the match details.",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = MatchResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not Found - Match with the specified ID was not found.",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized – token is missing or invalid.",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  public ResponseEntity<MatchResponse> getMatchById(@PathVariable UUID matchId) {
    return ResponseEntity.ok(matchService.getMatchById(matchId));
  }

  /**
   * Updates the result of a specific match.
   *
   * <p>Accessible only to users with the ADMIN role.
   *
   * @param matchId the UUID of the match
   * @param matchRequest the result update request body
   * @return the updated {@link MatchResponse}
   */
  @PutMapping("/{matchId}/result")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(
      summary = "Update match result",
      description =
          "Updates the result of a specific match. Only administrators can perform this action.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Ok - Match result successfully updated.",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = MatchResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request - Invalid request body or match data.",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not Found - Match with the specified ID was not found.",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized – token is missing or invalid.",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden – only administrators can update match results.",
            content =
                @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  public ResponseEntity<MatchResponse> updateMatchResult(
      @PathVariable UUID matchId, @RequestBody MatchRequest matchRequest) {
    return ResponseEntity.ok(matchService.updateMatchResult(matchId, matchRequest));
  }
}
