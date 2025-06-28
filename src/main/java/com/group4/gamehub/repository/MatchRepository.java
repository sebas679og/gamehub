package com.group4.gamehub.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.group4.gamehub.model.MatchEntity;

public interface MatchRepository extends JpaRepository<MatchEntity, UUID> {

    @Query("SELECT MAX(m.round) FROM MatchEntity m WHERE m.tournament.id = :tournamentId")
    Optional<Integer> findMaxRoundByTournamentId(@Param("tournamentId") UUID tournamentId);
}
