package com.group4.gamehub.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.group4.gamehub.exception.JwtAuthEntryPoint;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Security filter that validates JWT tokens on incoming HTTP requests.
 *
 * <p>It checks for the Authorization header, validates the token, and sets the authentication
 * context. If the token is invalid or missing, the request proceeds without authentication or
 * triggers an entry point response.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final CustomUserDetailsService userDetailsService;
  private final JwtAuthEntryPoint jwtAuthEntryPoint;

  /**
   * Constructs the filter with required dependencies.
   *
   * @param jwtService service for token validation and extraction
   * @param userDetailsService service to load user data from username
   * @param jwtAuthEntryPoint entry point to handle unauthorized access attempts
   */
  public JwtAuthFilter(
      JwtService jwtService,
      CustomUserDetailsService userDetailsService,
      JwtAuthEntryPoint jwtAuthEntryPoint) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
    this.jwtAuthEntryPoint = jwtAuthEntryPoint;
  }

  /**
   * Filters each request, checking if a valid JWT token is present and sets the authentication.
   *
   * @param request the incoming HTTP request
   * @param response the HTTP response
   * @param filterChain the filter chain to continue processing
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    // Skip if no Bearer token present
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String jwtToken = authHeader.substring(7);

    try {
      String username = jwtService.extractUsername(jwtToken);

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (jwtService.isTokenValid(jwtToken, userDetails)) {
          UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }

      filterChain.doFilter(request, response);

    } catch (JWTVerificationException e) {
      // Handle invalid token
      request.setAttribute("Exception", e);
      jwtAuthEntryPoint.commence(
          request,
          response,
          new org.springframework.security.authentication.BadCredentialsException(e.getMessage()));
    }
  }
}
