package com.group4.gamehub.controller;

import com.group4.gamehub.dto.requests.tournament.TournamentsRequest;
import com.group4.gamehub.dto.responses.tournament.JoinTournament;
import com.group4.gamehub.dto.responses.tournament.TournamentDetails;
import com.group4.gamehub.dto.responses.tournament.TournamentsResponse;
import com.group4.gamehub.service.tournamentservice.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    public static final String APPLICATION_JSON = MediaType.APPLICATION_JSON_VALUE;

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }


    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TournamentsResponse> createTournaments(TournamentsRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tournamentService.createTournaments(request));
    }

    @GetMapping("/")
    public ResponseEntity<TournamentsResponse> getTournaments() {
        return ResponseEntity.ok(tournamentService.getTournaments());
    }

    @GetMapping("/{slug}")
    public ResponseEntity<TournamentDetails> getTournamentDetails(String slug) {
        return ResponseEntity.ok(tournamentService.getTournamentDetails(slug));
    }

    @PostMapping("/{slug}/join")
    @PreAuthorize("hasAnyRole('PLAYER')")
    public ResponseEntity<JoinTournament> joinTournament(Authentication authentication, String slug) {
        return ResponseEntity.ok(tournamentService.joinTournament(authentication.getName(), slug));
    }
}
