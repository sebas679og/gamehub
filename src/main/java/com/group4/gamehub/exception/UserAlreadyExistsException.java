package com.group4.gamehub.exception;

/**
 * Exception thrown when an attempt is made to create a user that already exists, typically during
 * registration.
 */
public class UserAlreadyExistsException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a new {@code UserAlreadyExistsException} with the specified detail message.
   *
   * @param message the detail message explaining the cause of the exception
   */
  public UserAlreadyExistsException(String message) {
    super(message);
  }
}
