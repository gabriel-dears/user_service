package com.hospital_app.user_service.infra.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server().url("http://localhost:8000/user-service")
                                .description("Kong Gateway")
                ))
                .info(new Info().title("User Service API")
                        .description("API for managing users")
                        .version("1.0"));
    }

}
