package com.group4.gamehub.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.group4.gamehub.model.MatchEntity;

/**
 * Repository interface for managing {@link MatchEntity} persistence.
 * <p>
 * Extends {@link JpaRepository} to provide basic CRUD operations and
 * defines custom queries related to tournament matches.
 */
public interface MatchRepository extends JpaRepository<MatchEntity, UUID> {

    /**
     * Retrieves the maximum round number of matches associated with a given tournament ID.
     *
     * @param tournamentId the unique identifier of the tournament
     * @return an {@link Optional} containing the maximum round number, if any match exists
     */
    @Query("SELECT MAX(m.round) FROM MatchEntity m WHERE m.tournament.id = :tournamentId")
    Optional<Integer> findMaxRoundByTournamentId(@Param("tournamentId") UUID tournamentId);

    /**
     * Finds a match by its unique identifier.
     *
     * @param matchId the ID of the match
     * @return an {@link Optional} containing the match if found
     */
    Optional<MatchEntity> findById(UUID matchId);
}
