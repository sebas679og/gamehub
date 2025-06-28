package com.group4.gamehub.util;

/** Enumeration representing the possible outcomes of a match. */
public enum Result {

  /** The match has not yet been played or finalized. */
  PENDING,

  /** Player 1 won the match. */
  PLAYER1_WIN,

  /** Player 2 won the match. */
  PLAYER2_WIN,

  /** The match ended in a draw. */
  DRAW
}
