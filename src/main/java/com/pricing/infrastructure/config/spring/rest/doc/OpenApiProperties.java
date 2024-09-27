package com.pricing.infrastructure.config.spring.rest.doc;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app.openapi")
public class OpenApiProperties {

  private String version;
  private String applicationName;
  private String description;

}
