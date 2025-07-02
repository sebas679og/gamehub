package com.group4.gamehub.dto.responses.tournament;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.group4.gamehub.util.Status;

public class Tournament {

    private String slug;
    private String name;

    @JsonProperty(value = "max_players")
    private int maxPlayers;

    private Status status;
}
