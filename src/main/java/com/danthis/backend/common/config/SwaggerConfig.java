package com.danthis.backend.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  private static final String SECURITY_SCHEME_NAME = "BearerAuth";

  @Bean
  public OpenAPI customOpenAPI() {
    SecurityScheme securityScheme = new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .in(SecurityScheme.In.HEADER)
        .name("Authorization");

    SecurityRequirement securityRequirement = new SecurityRequirement().addList(SECURITY_SCHEME_NAME);

    return new OpenAPI()
        .info(new Info()
            .title("Danthis API Documentation")
            .description("API for Danthis")
            .version("1.0.0"))
        .addServersItem(new Server().url("/"))
        .schemaRequirement(SECURITY_SCHEME_NAME, securityScheme)
        .addSecurityItem(securityRequirement);
  }
}
