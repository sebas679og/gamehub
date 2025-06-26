package com.group4.gamehub.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
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
@Table(name = "messages")
public class MessageEntity {
  @Id @GeneratedValue private UUID id;

  private UUID senderId;

  private String content;

  private LocalDateTime timestamp;

  @ManyToOne
  @JoinColumn(name = "match_id")
  private MatchEntity matchEntity;

  @ManyToOne
  @JoinColumn(name = "tournament_id")
  private TournamentEntity tournament;
}
