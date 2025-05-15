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


        Server localServer = new Server();
        localServer.setUrl("http://localhost:8081");

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("accessTokenAuth", accessTokenAuth)
                        .addSecuritySchemes("refreshTokenAuth", refreshTokenAuth))
                .security(List.of(securityRequirement))
                .info(new Info()
                        .title("토킷(TOKKIT) ADMIN API 명세서")
                        .description("토킷(TOKKIT) ADMIN API 명세서입니다.")
                        .version("1.0.0"))
                .servers(List.of(localServer));
    }
}
