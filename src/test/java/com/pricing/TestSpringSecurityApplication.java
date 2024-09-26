package com.pricing;

import com.PricingApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = true)
public class TestSpringSecurityApplication {

  public static void main(String[] args) {
    SpringApplication.from(PricingApplication::main)
        .with(TestSpringSecurityApplication.class)
        .run(args);
  }

}
