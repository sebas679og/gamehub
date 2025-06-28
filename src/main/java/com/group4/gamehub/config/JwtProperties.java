package com.group4.gamehub.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for JSON Web Token (JWT) handling.
 *
 * <p>Values are loaded from the application's configuration file (e.g., {@code application.yml})
 * using the prefix {@code jwt}.
 *
 * <p>Example:
 *
 * <pre>
 * jwt.secret=yourSecretKey
 * jwt.expiration=86400000
 * </pre>
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {

  /** Secret key used to sign JWT tokens. */
  private String secret = "defaultSecret";

  /** Token expiration time in milliseconds (default is 24 hours). */
  private long expiration = 86400000L;
}
