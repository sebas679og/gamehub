package com.group4.gamehub.dto.responses.tournament;

import com.group4.gamehub.dto.responses.match.MatchResponse;
import com.group4.gamehub.dto.responses.user.UserTournament;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class TournamentDetails extends TournamentPlayers{

    private List<MatchResponse> matches;
    private List<UserTournament> users;
}
