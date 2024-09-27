package com.pricing.infrastructure.config.spring.database.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.pricing.infrastructure.database.springdata.repository")
public class SpringDataConfig {

}


