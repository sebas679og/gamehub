package com.group4.gamehub.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group4.gamehub.model.TournamentEntity;

public interface TournamentRepository extends JpaRepository<TournamentEntity, UUID> {
    
    Optional<TournamentEntity> findById(UUID tournamentId);
}
