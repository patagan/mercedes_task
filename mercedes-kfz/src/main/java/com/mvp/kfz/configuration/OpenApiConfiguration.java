package com.mvp.kfz.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OpenApiConfiguration {

    @Value("${openapi.title}")
    private String title;

    @Value("${openapi.version}")
    private String version;

    @Value("${openapi.description}")
    private String description;

    @Bean
    public OpenAPI customOpenAPI() {
        log.info("Generation custom OpenApi with contextPath: {}, title: {}, version: {}, description: {}", title, version, description);
        return new OpenAPI().info(new Info().title(title).description(description).version(version))
                .addSecurityItem(new SecurityRequirement().addList("bearer-auth"))
                .components(new Components()
                        .addSecuritySchemes("bearer-auth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

}