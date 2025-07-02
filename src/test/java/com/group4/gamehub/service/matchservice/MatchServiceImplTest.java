package com.group4.gamehub.service.matchservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.group4.gamehub.dto.requests.match.Match;
import com.group4.gamehub.dto.responses.match.Matchs;
import com.group4.gamehub.exception.NotFoundException;
import com.group4.gamehub.mapper.MatchMapper;
import com.group4.gamehub.model.MatchEntity;
import com.group4.gamehub.model.TournamentEntity;
import com.group4.gamehub.model.UserEntity;
import com.group4.gamehub.repository.MatchRepository;
import com.group4.gamehub.repository.TournamentRepository;
import com.group4.gamehub.util.Result;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MatchServiceImplTest {

  @Mock private MatchRepository matchRepository;
  @Mock private TournamentRepository tournamentRepository;
  @Mock private MatchMapper matchMapper;

  @InjectMocks private MatchServiceImpl matchService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void generateMatchesForTournament_ReturnsMatchResponses() {
    UUID tournamentId = UUID.randomUUID();
    TournamentEntity tournament = mock(TournamentEntity.class);
    UserEntity player1 = mock(UserEntity.class);
    UserEntity player2 = mock(UserEntity.class);
    List<UserEntity> players = Arrays.asList(player1, player2);

    when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));
    when(tournament.getUserEntities()).thenReturn(new HashSet<>(players));
    when(matchRepository.findMaxRoundByTournamentId(tournamentId)).thenReturn(Optional.of(0));

    MatchEntity matchEntity = mock(MatchEntity.class);
    List<MatchEntity> matchEntities = List.of(matchEntity);
    when(matchRepository.saveAll(anyList())).thenReturn(matchEntities);

    com.group4.gamehub.dto.responses.match.Match match = mock(com.group4.gamehub.dto.responses.match.Match.class);
    when(matchMapper.toMatchResponse(matchEntity)).thenReturn(match);

    Matchs result = matchService.generateMatchesForTournament(tournamentId);

    assertEquals(1, result.getMatches().size());
    assertEquals(match, result.getMatches().get(0));
    verify(matchRepository).saveAll(anyList());
  }

  @Test
  void generateMatchesForTournament_ThrowsIfNotEnoughPlayers() {
    UUID tournamentId = UUID.randomUUID();
    TournamentEntity tournament = mock(TournamentEntity.class);
    when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));
    when(tournament.getUserEntities()).thenReturn(new HashSet<>());

    RuntimeException ex =
        assertThrows(
            RuntimeException.class, () -> matchService.generateMatchesForTournament(tournamentId));
    assertEquals("Not enough players", ex.getMessage());
  }

  @Test
  void getMatchById_ReturnsMatchResponse() {
    UUID matchId = UUID.randomUUID();
    MatchEntity matchEntity = mock(MatchEntity.class);
    com.group4.gamehub.dto.responses.match.Match match = mock(com.group4.gamehub.dto.responses.match.Match.class);

    when(matchRepository.findById(matchId)).thenReturn(Optional.of(matchEntity));
    when(matchMapper.toMatchResponse(matchEntity)).thenReturn(match);

    com.group4.gamehub.dto.responses.match.Match result = matchService.getMatchById(matchId);

    assertEquals(match, result);
  }

  @Test
  void getMatchById_ThrowsIfNotFound() {
    UUID matchId = UUID.randomUUID();
    when(matchRepository.findById(matchId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> matchService.getMatchById(matchId));
  }

  @Test
  void updateMatchResult_UpdatesAndReturnsMatchResponse() {
    UUID matchId = UUID.randomUUID();
    Match request = mock(Match.class);
    MatchEntity matchEntity = mock(MatchEntity.class);
    MatchEntity updatedEntity = mock(MatchEntity.class);
    com.group4.gamehub.dto.responses.match.Match match = mock(com.group4.gamehub.dto.responses.match.Match.class);

    when(matchRepository.findById(matchId)).thenReturn(Optional.of(matchEntity));
    when(matchEntity.getResult()).thenReturn(Result.PENDING);
    when(matchRepository.save(matchEntity)).thenReturn(updatedEntity);
    when(matchMapper.toMatchResponse(updatedEntity)).thenReturn(match);

    com.group4.gamehub.dto.responses.match.Match result = matchService.updateMatchResult(matchId, request);

    verify(matchEntity).setResult(request.getResult());
    assertEquals(match, result);
  }

  @Test
  void updateMatchResult_ThrowsIfResultAlreadySet() {
    UUID matchId = UUID.randomUUID();
    Match request = mock(Match.class);
    MatchEntity matchEntity = mock(MatchEntity.class);

    when(matchRepository.findById(matchId)).thenReturn(Optional.of(matchEntity));
    when(matchEntity.getResult()).thenReturn(Result.PLAYER1_WIN);

    assertThrows(RuntimeException.class, () -> matchService.updateMatchResult(matchId, request));
  }

  @Test
  void updateMatchResult_ThrowsIfNotFound() {
    UUID matchId = UUID.randomUUID();
    Match request = mock(Match.class);

    when(matchRepository.findById(matchId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> matchService.updateMatchResult(matchId, request));
  }
}
