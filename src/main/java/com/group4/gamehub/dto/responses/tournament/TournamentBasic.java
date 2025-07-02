package com.group4.gamehub.dto.responses.tournament;

import com.group4.gamehub.util.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class TournamentBasic {

    private String slug;
    private String name;
    private Status status;
}
