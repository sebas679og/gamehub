package com.group4.gamehub.model;

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
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "matchs")
public class MatchEntity {

  @Id @GeneratedValue @UuidGenerator private UUID id;

  @ManyToOne(
      targetEntity = TournamentEntity.class,
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER)
  @JoinColumn(name = "tournament_id", nullable = false)
  private TournamentEntity tournament;

  @ManyToOne
  @JoinColumn(name = "player1_id", nullable = false)
  private UserEntity player1;

  @ManyToOne
  @JoinColumn(name = "player2_id", nullable = false)
  private UserEntity player2;

  @Enumerated(EnumType.STRING)
  private Result result;

  private int round;
}
