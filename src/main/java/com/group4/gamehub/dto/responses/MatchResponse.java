package com.group4.gamehub.dto.responses;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MatchResponse {
    private UUID tournament;

    @JsonProperty("tournament_name")
    private String tournamentName;

    private String player1;
    private String player2;
    private int round;
    private String result;
}
