package com.group4.gamehub.model;

import com.group4.gamehub.util.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity representing a tournament where users can participate in matches. */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tournaments")
public class TournamentEntity {

  /** Unique identifier for the tournament. */
  @Id @GeneratedValue private UUID id;

  /** Name of the tournament. */
  @Column(nullable = false)
  private String name;

  /** Maximum number of players allowed in the tournament. */
  @Column(nullable = false)
  private Integer maxPlayers;

  /** Current status of the tournament (e.g., PENDING, ACTIVE, FINISHED). */
  @Enumerated(EnumType.STRING)
  private Status status;

  /** Set of users participating in the tournament. This is a many-to-many relationship. */
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = UserEntity.class)
  @JoinTable(
      joinColumns = @JoinColumn(name = "tournament_tbl"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<UserEntity> userEntities;
}
