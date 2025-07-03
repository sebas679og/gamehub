package com.group4.gamehub.dto.responses.tournament;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JoinTournament {
    private String message;

    @JsonProperty("tournament_slug")
    private String tournamentSlug;

    @JsonProperty("tournament_name")
    private String tournamentName;

    private String username;

}
