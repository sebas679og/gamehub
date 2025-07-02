package com.group4.gamehub.dto.responses.tournament;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class TournamentsResponse {
    private List<TournamentResponse> tournamentResponses;
}
