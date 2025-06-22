package com.gestor.gestorcursos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(new Info()
        .title("Gestor de cursos por José Velásquez")
        .version("1.0")
        .description("Micro servicio de gestión de cursos para proyecto semestral en Spring Boot")
        );
    }
}
