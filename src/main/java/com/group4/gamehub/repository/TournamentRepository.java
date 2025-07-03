package com.group4.gamehub.repository;

import com.group4.gamehub.model.TournamentEntity;
import com.group4.gamehub.util.Status;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
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

  /**
   * Retrieves a tournament by its unique slug, including associated users and matches.
   *
   * @param slug the unique slug of the tournament
   * @return an {@link Optional} containing the tournament with its relationships, if found
   */
  @EntityGraph(attributePaths = {"userEntities", "matches"})
  Optional<TournamentEntity> findBySlug(String slug);

  /**
   * Checks if a tournament exists with the specified slug.
   *
   * @param slug the unique slug to check
   * @return {@code true} if a tournament exists with the given slug, {@code false} otherwise
   */
  boolean existsBySlug(String slug);

  /**
   * Retrieves all tournaments that match a given status.
   *
   * @param status the {@link Status} of the tournaments to retrieve
   * @return a list of tournaments with the specified status
   */
  List<TournamentEntity> findAllByStatus(Status status);
}
