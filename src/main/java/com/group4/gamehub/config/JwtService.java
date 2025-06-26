package com.group4.gamehub.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final String secret;
  private final long expiration;
  private final Algorithm algorithm;

  public JwtService(JwtProperties jwtProperties) {
    if (jwtProperties == null || jwtProperties.getSecret() == null) {
      throw new IllegalArgumentException("JWT properties or secret cannot be null");
    }

    String secret = jwtProperties.getSecret();
    long expiration = jwtProperties.getExpiration();

    this.secret = secret;
    this.expiration = expiration;
    this.algorithm = Algorithm.HMAC256(this.secret);
  }

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

  public boolean isTokenValid(String token, UserDetails userDetails) {
    String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  public String extractUsername(String token) {
    return decodeToken(token).getSubject();
  }

  private boolean isTokenExpired(String token) {
    return decodeToken(token).getExpiresAt().before(new Date());
  }

  private DecodedJWT decodeToken(String token) {
    return JWT.require(algorithm).build().verify(token);
  }
}
