package com.group4.gamehub.dto.responses;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) for representing standardized error responses. Typically returned when
 * an exception or validation error occurs in the API.
 */
@Getter
@Builder
public class ErrorResponse {

  /** HTTP status code of the error (e.g., 404, 500). */
  private final int code;

  /** Name or short identifier of the error (e.g., "NotFound", "InternalServerError"). */
  private final String name;

  /** Detailed description of the error. */
  private final String description;

  /** The URI of the endpoint where the error occurred. */
  private final String uri;

  /** HTTP method (e.g., GET, POST) used in the request that caused the error. */
  private final String method;

  /**
   * Timestamp indicating when the error occurred. Defaults to the current instant at the time of
   * object creation.
   */
  @Builder.Default
  private final Instant timestamp = Instant.ofEpochMilli(Instant.now().toEpochMilli());
}
