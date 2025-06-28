package com.group4.gamehub.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Service for generating and validating JSON Web Tokens (JWT).
 *
 * <p>This service handles token creation with expiration, role claims, and secure verification.
 */
@Service
public class JwtService {

  private final String secret;
  private final long expiration;
  private final Algorithm algorithm;

  /**
   * Constructs the service with secret and expiration values from {@link JwtProperties}.
   *
   * @param jwtProperties configuration properties for JWT
   * @throws IllegalArgumentException if the properties or secret are null
   */
  public JwtService(JwtProperties jwtProperties) {
    if (jwtProperties == null || jwtProperties.getSecret() == null) {
      throw new IllegalArgumentException("JWT properties or secret cannot be null");
    }

    this.secret = jwtProperties.getSecret();
    this.expiration = jwtProperties.getExpiration();
    this.algorithm = Algorithm.HMAC256(this.secret);
  }

  /**
   * Generates a signed JWT for the given authenticated user.
   *
   * @param userDetails the authenticated user's details
   * @return a signed JWT token as a string
   */
  public String generateToken(UserDetails userDetails) {
    return JWT.create()
        .withSubject(userDetails.getUsername())
        .withClaim(
            "role",
            userDetails.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .orElse("PLAYER"))
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
        .sign(algorithm);
  }

  /**
   * Validates a JWT by checking username match and expiration.
   *
   * @param token the JWT to validate
   * @param userDetails the authenticated user's details
   * @return true if the token is valid, false otherwise
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  /**
   * Extracts the username from a JWT token.
   *
   * @param token the JWT token
   * @return the subject (username) stored in the token
   */
  public String extractUsername(String token) {
    return decodeToken(token).getSubject();
  }

  /**
   * Checks if a token has expired.
   *
   * @param token the JWT token
   * @return true if expired, false otherwise
   */
  private boolean isTokenExpired(String token) {
    return decodeToken(token).getExpiresAt().before(new Date());
  }

  /**
   * Verifies and decodes a JWT token.
   *
   * @param token the raw JWT token
   * @return the decoded JWT
   * @throws JWTVerificationException if the token is invalid or tampered
   */
  private DecodedJWT decodeToken(String token) {
    return JWT.require(algorithm).build().verify(token);
  }
}
