package com.group4.gamehub.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity representing a chat message sent by a user during a match or tournament. */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class MessageEntity {

  /** Unique identifier for the message. */
  @Id @GeneratedValue private UUID id;

  /** ID of the user who sent the message. */
  private UUID senderId;

  /** Text content of the message. */
  private String content;

  /** Timestamp indicating when the message was sent. */
  private LocalDateTime timestamp;

  /** Match in which the message was sent, if applicable. */
  @ManyToOne
  @JoinColumn(name = "match_id")
  private MatchEntity matchEntity;

  /** Tournament in which the message was sent, if applicable. */
  @ManyToOne
  @JoinColumn(name = "tournament_id")
  private TournamentEntity tournament;
}
