package com.group4.gamehub.service.tournamentservice;

import com.group4.gamehub.dto.requests.tournament.TournamentsRequest;
import com.group4.gamehub.dto.responses.tournament.JoinTournament;
import com.group4.gamehub.dto.responses.tournament.TournamentDetails;
import com.group4.gamehub.dto.responses.tournament.TournamentsResponse;

public interface TournamentService {

    TournamentsResponse createTournaments(TournamentsRequest request);

    TournamentsResponse getTournaments();

    TournamentDetails getTournamentDetails(String slug);

    JoinTournament joinTournament(String username, String slug);
}
