package com.group4.gamehub.controller;

import com.group4.gamehub.dto.requests.MatchRequest;
import com.group4.gamehub.dto.responses.MatchResponse;
import com.group4.gamehub.service.matchservice.MatchService;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MatchControllerTest {

    @Mock
    private MatchService matchService;

    @InjectMocks
    private MatchController matchController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateMatches_ReturnsCreatedAndList(){
        UUID tournamentId = UUID.randomUUID();
        MatchResponse matchResponse1 = mock(MatchResponse.class);
        MatchResponse matchResponse2 = mock(MatchResponse.class);
        List<MatchResponse> expected = Arrays.asList(matchResponse1, matchResponse2);
        when(matchService.generateMatchesForTournament(tournamentId)).thenReturn(expected);

        ResponseEntity<List<MatchResponse>> response = matchController.generateMatches(tournamentId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(matchService, times(1)).generateMatchesForTournament(tournamentId);
    }

    @Test
    void getMatchById_ReturnsOkAndMatchResponse() {
        UUID matchId = UUID.randomUUID();
        MatchResponse expected = mock(MatchResponse.class);
        when(matchService.getMatchById(matchId)).thenReturn(expected);

        ResponseEntity<MatchResponse> response = matchController.getMatchById(matchId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(matchService, times(1)).getMatchById(matchId);
    }

    @Test
    void updateMatchResult_ReturnsOkAndUpdatedMatchResponse() {
        UUID matchId = UUID.randomUUID();
        MatchRequest request = new MatchRequest();
        MatchResponse expected = mock(MatchResponse.class);
        when(matchService.updateMatchResult(matchId, request)).thenReturn(expected);

        ResponseEntity<MatchResponse> response = matchController.updateMatchResult(matchId, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(matchService, times(1)).updateMatchResult(matchId, request);
    }
}
