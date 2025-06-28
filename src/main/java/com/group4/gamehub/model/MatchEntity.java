package com.group4.gamehub.model;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.group4.gamehub.util.Result;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity representing a match between two players within a tournament. */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "matchs")
public class MatchEntity {

  /** Unique identifier for the match. */
  @Id @GeneratedValue @UuidGenerator private UUID id;

  /**
   * The tournament to which this match belongs. This relationship is mandatory and uses eager
   * loading.
   */
  @ManyToOne(
      targetEntity = TournamentEntity.class,
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER)
  @JoinColumn(name = "tournament_id", nullable = false)
  private TournamentEntity tournament;

  /** The first player participating in the match. */
  @ManyToOne
  @JoinColumn(name = "player1_id", nullable = false)
  private UserEntity player1;

  /** The second player participating in the match. */
  @ManyToOne
  @JoinColumn(name = "player2_id", nullable = true)
  private UserEntity player2;

  /** The result of the match (e.g., WIN, LOSS, DRAW). */
  @Enumerated(EnumType.STRING)
  private Result result;

  /** The round number in which the match takes place within the tournament. */
  private int round;
}
