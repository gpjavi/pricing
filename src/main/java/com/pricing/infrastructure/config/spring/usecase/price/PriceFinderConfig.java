package com.pricing.infrastructure.config.spring.usecase.price;


import com.pricing.application.usecase.price.PriceFinder;
import com.pricing.application.usecase.price.validator.PriceFinderValidator;
import com.pricing.domain.repository.price.PriceQueryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PriceFinderConfig {

  @Bean
  public PriceFinder priceFinder(PriceQueryRepository queryRepository,
      PriceFinderValidator priceFinderValidator) {
    return new PriceFinder(queryRepository, priceFinderValidator);
  }
}
