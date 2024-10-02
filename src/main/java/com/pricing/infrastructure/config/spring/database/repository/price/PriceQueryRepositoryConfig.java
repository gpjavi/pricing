package com.pricing.infrastructure.config.spring.database.repository.price;


import com.pricing.domain.repository.price.PriceQueryRepository;
import com.pricing.infrastructure.database.springdata.mapper.PriceEntityMapper;
import com.pricing.infrastructure.database.springdata.repository.price.PriceEntityJpaRepository;
import com.pricing.infrastructure.database.springdata.repository.price.PriceQueryRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PriceQueryRepositoryConfig {

  @Bean
  public PriceQueryRepository priceQueryRepository(PriceEntityJpaRepository jpaRepository,
      PriceEntityMapper priceEntityMapper) {
    return new PriceQueryRepositoryImpl(jpaRepository, priceEntityMapper);
  }
}
