package com.group4.gamehub.dto.responses.tournament;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class TournamentPlayers extends TournamentBasic{

    @JsonProperty(value = "max_players")
    private int maxPlayers;

}
