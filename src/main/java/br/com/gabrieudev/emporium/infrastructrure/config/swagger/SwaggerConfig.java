package br.com.gabrieudev.emporium.infrastructrure.config.swagger;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

        @Bean
        OpenAPI emporiumOpenAPI() {
                return new OpenAPI()
                                .info(new Info().title("API Emporium")
                                                .description("API do projeto Emporium")
                                                .version("v0.0.1")
                                                .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                                .servers(List.of(
                                                new Server().url("https://emporium-production.up.railway.app/api/v1")
                                                                .description("Servidor de Produção"),
                                                new Server().url("http://localhost:8080/api/v1")
                                                                .description("Servidor Local")))
                                .components(new io.swagger.v3.oas.models.Components()
                                                .addSecuritySchemes("BearerAuth", new SecurityScheme()
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")))
                                .externalDocs(new ExternalDocumentation()
                                                .description("Repositório do projeto")
                                                .url("https://github.com/gabrieudev/emporium/"));
        }
}
