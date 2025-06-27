package com.group4.gamehub.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for the default admin user.
 *
 * <p>Values are loaded from application properties or YAML using the prefix {@code admin}. For
 * example:
 *
 * <pre>
 * admin.username=admin
 * admin.password=admin1234
 * admin.email=admin@gamehub.com
 * </pre>
 */
@Configuration
@ConfigurationProperties(prefix = "admin")
@Getter
@Setter
public class AdminProperties {

  /** Default admin username. */
  private String username = "admin";

  /** Default admin password. */
  private String password = "admin1234";

  /** Default admin email address. */
  private String email = "admin@gamehub.com";
}
