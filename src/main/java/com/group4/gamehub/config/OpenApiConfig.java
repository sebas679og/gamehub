package com.group4.gamehub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GameHub API")
                        .version("1.0")
                        .description("REST API that allows the creation and management of tournaments, automatic pairing of players, basic chat, ranking and results update.")
                );
    }
}
