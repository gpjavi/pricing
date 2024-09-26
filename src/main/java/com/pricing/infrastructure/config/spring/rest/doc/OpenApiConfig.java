package com.pricing.infrastructure.config.spring.rest.doc;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@OpenAPIDefinition(servers = {@Server(url = "${app.openapi.server-url}")})
public class OpenApiConfig {

  private final OpenApiProperties openApiProperties;

  @Bean
  public OpenAPI customizeOpenApi() {
    return new OpenAPI()
        .info(new Info()
            .title(openApiProperties.getApplicationName())
            .description(openApiProperties.getDescription())
            .version(openApiProperties.getVersion()));
  }

}