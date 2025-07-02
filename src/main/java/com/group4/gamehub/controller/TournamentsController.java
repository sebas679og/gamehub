package com.group4.gamehub.controller;

import com.group4.gamehub.dto.responses.tournament.TournamentsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentsController {

    public static final String APPLICATION_JSON = MediaType.APPLICATION_JSON_VALUE;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TournamentsResponse> createTournaments(TournamentsResponse tournamentsResponse) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tournamentsResponse);
    }
}
