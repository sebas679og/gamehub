package com.group4.gamehub.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) that represents the response for a match.
 * <p>
 * This object is returned by endpoints to provide relevant match information,
 * including player names, tournament, round, and result.
 */
@Setter
@Getter
@Builder
public class MatchResponse {

    /**
     * Name of the tournament to which the match belongs.
     */
    @JsonProperty("tournament_name")
    private String tournamentName;

    /**
     * Username of the first player.
     */
    private String player1;

    /**
     * Username of the second player.
     */
    private String player2;

    /**
     * Round number of the match (e.g., 1 for first round).
     */
    private int round;

    /**
     * Result of the match (e.g., "PLAYER1_WIN", "PLAYER2_WIN", "PENDING" or "DRAW").
     */
    private String result;
}

