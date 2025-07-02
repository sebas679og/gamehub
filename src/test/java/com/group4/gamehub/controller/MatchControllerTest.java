package com.group4.gamehub.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.group4.gamehub.dto.requests.match.Match;
import com.group4.gamehub.dto.responses.match.Matchs;
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

class MatchControllerTest {

  @Mock private MatchService matchService;

  @InjectMocks private MatchController matchController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void generateMatches_ReturnsCreatedAndMatchsResponse() {
    UUID tournamentId = UUID.randomUUID();
    com.group4.gamehub.dto.responses.match.Match match1 = mock(com.group4.gamehub.dto.responses.match.Match.class);
    com.group4.gamehub.dto.responses.match.Match match2 = mock(com.group4.gamehub.dto.responses.match.Match.class);
    Matchs expected = Matchs.builder()
            .matches(Arrays.asList(match1, match2))
            .build();
    when(matchService.generateMatchesForTournament(tournamentId)).thenReturn(expected);

    ResponseEntity<Matchs> response = matchController.generateMatches(tournamentId);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(expected, response.getBody());
    verify(matchService, times(1)).generateMatchesForTournament(tournamentId);
  }

  @Test
  void getMatchById_ReturnsOkAndMatchResponse() {
    UUID matchId = UUID.randomUUID();
    com.group4.gamehub.dto.responses.match.Match expected = mock(com.group4.gamehub.dto.responses.match.Match.class);
    when(matchService.getMatchById(matchId)).thenReturn(expected);

    ResponseEntity<com.group4.gamehub.dto.responses.match.Match> response = matchController.getMatchById(matchId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expected, response.getBody());
    verify(matchService, times(1)).getMatchById(matchId);
  }

  @Test
  void updateMatchResult_ReturnsOkAndUpdatedMatchResponse() {
    UUID matchId = UUID.randomUUID();
    Match request = new Match();
    com.group4.gamehub.dto.responses.match.Match expected = mock(com.group4.gamehub.dto.responses.match.Match.class);
    when(matchService.updateMatchResult(matchId, request)).thenReturn(expected);

    ResponseEntity<com.group4.gamehub.dto.responses.match.Match> response = matchController.updateMatchResult(matchId, request);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expected, response.getBody());
    verify(matchService, times(1)).updateMatchResult(matchId, request);
  }
}
