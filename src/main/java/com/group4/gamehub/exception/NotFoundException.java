package com.group4.gamehub.exception;

/**
 * Exception thrown when a user cannot be found in the system. Typically used in user retrieval
 * operations (e.g., by ID, username, or email).
 */
public class NotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a new {@code UserNotFoundException} with the specified detail message.
   *
   * @param message the detail message explaining the cause of the exception
   */
  public NotFoundException(String message) {
    super(message);
  }
}
