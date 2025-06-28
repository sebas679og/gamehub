package com.group4.gamehub.service.tournamentservice;

import com.group4.gamehub.dto.requests.MatchRequest;
import com.group4.gamehub.dto.responses.MatchResponse;

import java.util.List;
import java.util.UUID;

public interface MatchService {

    List<MatchResponse> generateMatchesForTournament(UUID tournamentId);

    MatchResponse getMatchById(UUID matchId);

    MatchResponse updateMatchResult(UUID matchId, MatchRequest matchRequest);
}
