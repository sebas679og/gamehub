package com.group4.gamehub.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for customizing the OpenAPI (Swagger) documentation.
 *
 * <p>Defines general API metadata such as title, version, and description, which are displayed in
 * the Swagger UI.
 */
@Configuration
public class OpenApiConfig {

  /**
   * Creates and configures the OpenAPI documentation metadata.
   *
   * @return a custom {@link OpenAPI} instance for Swagger UI.
   */
  @Bean
  public OpenAPI customOpenApi() {
    String description =
        String.join(
            " ",
            "REST API that allows the creation and management of tournaments,",
            "automatic pairing of players, basic chat, ranking, and results update.");

    return new OpenAPI()
        .info(new Info().title("GameHub API").version("1.0").description(description));
  }
}
