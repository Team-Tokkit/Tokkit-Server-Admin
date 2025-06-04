package dev.admin.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        SecurityScheme accessTokenAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");


        SecurityScheme refreshTokenAuth = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.COOKIE)
                .name("refreshToken");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("accessTokenAuth")
                .addList("refreshTokenAuth");


        Server defaultServer = new Server()
                .url("http://35.216.99.149:8080")
                .description("Default GCP server");

        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("Local development server");
        Server prodServer = new Server()
                .url("https://api.tokkit.site:8080")
                .description("Local development server");

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("accessTokenAuth", accessTokenAuth)
                        .addSecuritySchemes("refreshTokenAuth", refreshTokenAuth))
                .security(List.of(securityRequirement))
                .info(new Info()
                        .title("토킷(TOKKIT) ADMIN API 명세서")
                        .description("토킷(TOKKIT) ADMIN API 명세서입니다.")
                        .version("1.0.0"))
                .servers(List.of(defaultServer, localServer,prodServer));
    }
}
