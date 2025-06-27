package com.group4.gamehub.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "admin")
@Getter
@Setter
public class AdminProperties {
  private String username = "admin";
  private String password = "admin1234";
  private String email = "admin@gamehub.com";
}
