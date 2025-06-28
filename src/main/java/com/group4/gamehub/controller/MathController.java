package com.group4.gamehub.controller;

import java.util.List;
import java.util.UUID;

import com.group4.gamehub.dto.responses.MatchResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.group4.gamehub.service.tournamentservice.MatchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/math")
public class MathController {

    private final MatchService matchService;

    public MathController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/generate/{tournamentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MatchResponse>> generateMatches(@PathVariable UUID tournamentId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(matchService.generateMatchesForTournament(tournamentId));
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<MatchResponse> getMatchById(@PathVariable UUID matchId) {
        return ResponseEntity.ok(matchService.getMatchById(matchId));
    }
}
