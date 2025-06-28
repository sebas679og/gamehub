package com.group4.gamehub.exception;

import com.group4.gamehub.dto.responses.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the REST API. Catches specific and general exceptions and converts
 * them into standardized {@link ErrorResponse} objects.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles generic {@link RuntimeException}.
   *
   * @param ex the thrown exception
   * @param request the HTTP request that caused the exception
   * @return a BAD_REQUEST response with an {@link ErrorResponse} body
   */
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleRuntime(
      RuntimeException ex, HttpServletRequest request) {
    return badRequest(ex.getMessage(), request);
  }

  /**
   * Handles {@link UserAlreadyExistsException} when a user tries to register with existing
   * credentials.
   *
   * @param ex the thrown exception
   * @param request the HTTP request that caused the exception
   * @return a CONFLICT response with an {@link ErrorResponse} body
   */
  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleUserAlreadyExists(
      UserAlreadyExistsException ex, HttpServletRequest request) {
    return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
  }

  /**
   * Handles {@link UserNotFoundException} when a requested user cannot be found.
   *
   * @param ex the thrown exception
   * @param request the HTTP request that caused the exception
   * @return a NOT_FOUND response with an {@link ErrorResponse} body
   */
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFound(
      UserNotFoundException ex, HttpServletRequest request) {
    return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
  }

  /**
   * Handles validation errors for DTOs annotated with validation constraints (e.g.
   * {@code @NotBlank}).
   *
   * @param ex the thrown exception
   * @param request the HTTP request that caused the exception
   * @return a BAD_REQUEST response with an {@link ErrorResponse} body containing validation details
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidation(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    String description =
        ex.getBindingResult().getFieldErrors().stream()
            .map(err -> err.getField() + ": " + err.getDefaultMessage())
            .collect(Collectors.joining("; "));
    return badRequest(description, request);
  }

  /**
   * Helper method for building a 400 BAD_REQUEST error response.
   *
   * @param message the error message
   * @param request the request context
   * @return a BAD_REQUEST {@link ErrorResponse}
   */
  private ResponseEntity<ErrorResponse> badRequest(String message, HttpServletRequest request) {
    return buildErrorResponse(HttpStatus.BAD_REQUEST, message, request);
  }

  /**
   * Builds a standardized {@link ErrorResponse} using the given status and request metadata.
   *
   * @param status the HTTP status code
   * @param description the detailed error message
   * @param request the HTTP request that caused the error
   * @return a {@link ResponseEntity} with the appropriate status and error body
   */
  private ResponseEntity<ErrorResponse> buildErrorResponse(
      HttpStatus status, String description, HttpServletRequest request) {
    ErrorResponse body =
        ErrorResponse.builder()
            .code(status.value())
            .name(status.getReasonPhrase())
            .description(description)
            .uri(request.getRequestURI())
            .method(request.getMethod())
            .build();

    return new ResponseEntity<>(body, status);
  }
}
