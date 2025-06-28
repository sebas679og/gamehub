package com.group4.gamehub.config;

import com.group4.gamehub.exception.JwtAuthEntryPoint;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration class.
 *
 * <p>Enables JWT-based stateless security, method-level authorization, and sets up custom
 * authentication entry points and filters.
 */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

  /**
   * Configures the HTTP security filter chain for the application.
   *
   * <p>- Disables CSRF (not needed for stateless APIs) - Sets a custom entry point for
   * authentication errors - Permits access to public endpoints (e.g., /api/auth, Swagger) -
   * Requires authentication for all other endpoints - Adds a custom JWT filter before the
   * UsernamePasswordAuthenticationFilter
   *
   * @param http the {@link HttpSecurity} to configure
   * @param jwtAuthFilter the custom JWT filter
   * @param entryPoint the custom entry point for handling unauthorized access
   * @return the configured {@link SecurityFilterChain}
   * @throws Exception in case of misconfiguration
   */
  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http, JwtAuthFilter jwtAuthFilter, JwtAuthEntryPoint entryPoint)
      throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(eh -> eh.authenticationEntryPoint(entryPoint))
        .authorizeHttpRequests(
            auth -> {
              auth.requestMatchers(
                      "/api/auth/**",
                      "/swagger-ui/**",
                      "/swagger-ui.html",
                      "/v3/api-docs/**",
                      "/v3/api-docs.yaml")
                  .permitAll()
                  .anyRequest()
                  .authenticated();
            })
        .addFilterBefore(
            jwtAuthFilter,
            org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
                .class);

    return http.build();
  }

  /**
   * Provides a {@link PasswordEncoder} bean using BCrypt hashing algorithm.
   *
   * @return the password encoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Provides the {@link AuthenticationManager} bean from configuration.
   *
   * @param config the authentication configuration
   * @return the authentication manager
   * @throws Exception if configuration fails
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }
}
