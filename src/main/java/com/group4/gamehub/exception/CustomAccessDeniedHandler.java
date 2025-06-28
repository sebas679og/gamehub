package com.group4.gamehub.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.group4.gamehub.dto.responses.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * Handler for forbidden access attempts (HTTP 403) when the user is authenticated but does not have
 * the required permissions to access a resource.
 *
 * <p>This class implements {@link AccessDeniedHandler} and constructs a standardized JSON response
 * for authorization failures.
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  /**
   * Handles access denied exceptions by returning a structured JSON error response with HTTP status
   * 403 (Forbidden).
   *
   * @param request the HTTP request that triggered the access denial
   * @param response the HTTP response to write the error details to
   * @param accessDeniedException the exception thrown due to insufficient permissions
   * @throws IOException if an I/O error occurs when writing the response
   */
  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException {

    HttpStatus status = HttpStatus.FORBIDDEN;

    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .code(status.value())
            .name(status.getReasonPhrase())
            .description("Access denied: insufficient permissions.")
            .method(request.getMethod())
            .uri(request.getRequestURI())
            .build();

    mapper.writeValue(response.getOutputStream(), errorResponse);
  }
}
