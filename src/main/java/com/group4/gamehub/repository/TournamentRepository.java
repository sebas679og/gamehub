package com.group4.gamehub.repository;

import com.group4.gamehub.model.TournamentEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link TournamentEntity} persistence.
 *
 * <p>Extends {@link JpaRepository} to provide standard CRUD operations for tournaments.
 */
public interface TournamentRepository extends JpaRepository<TournamentEntity, UUID> {

  /**
   * Retrieves a tournament by its unique identifier.
   *
   * @param tournamentId the UUID of the tournament to retrieve
   * @return an {@link Optional} containing the tournament, if found
   */
  @Override
  Optional<TournamentEntity> findById(UUID tournamentId);

  Optional<TournamentEntity> findBySlug(String slug);

  boolean existsBySlug(String slug);
}
