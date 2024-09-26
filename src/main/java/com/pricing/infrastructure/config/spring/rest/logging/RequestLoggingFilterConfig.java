package com.pricing.infrastructure.config.spring.rest.logging;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
@ConditionalOnProperty(
    prefix = "logging.custom",
    name = "request-logging-filter",
    havingValue = "true"
)
public class RequestLoggingFilterConfig {

  @Bean
  public CommonsRequestLoggingFilter logFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
    filter.setIncludeQueryString(true);
    filter.setIncludePayload(true);
    filter.setIncludeHeaders(true);
    filter.setMaxPayloadLength(10000);
    filter.setAfterMessagePrefix("REQUEST DATA: ");
    return filter;
  }
}
