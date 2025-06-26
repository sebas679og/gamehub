package com.group4.gamehub.model;

import com.group4.gamehub.util.Status;
import jakarta.persistence.*;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tournaments")
public class TournamentEntity {
  @Id @GeneratedValue private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Integer maxPlayers;

  @Enumerated(EnumType.STRING)
  private Status status;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = UserEntity.class)
  @JoinTable(
      joinColumns = @JoinColumn(name = "tournament_tbl"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<UserEntity> userEntities;
}
