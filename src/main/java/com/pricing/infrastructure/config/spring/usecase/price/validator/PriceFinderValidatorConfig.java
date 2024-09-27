package com.pricing.infrastructure.config.spring.usecase.price.validator;


import com.pricing.application.usecase.price.validator.PriceFinderValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PriceFinderValidatorConfig {

  @Bean
  public PriceFinderValidator priceFinderValidator() {
    return new PriceFinderValidator();
  }
}
