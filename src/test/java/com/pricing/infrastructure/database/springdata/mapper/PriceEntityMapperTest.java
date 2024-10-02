package com.pricing.infrastructure.database.springdata.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.pricing.domain.model.price.Price;
import com.pricing.infrastructure.database.springdata.model.jpa.brand.BrandEntity;
import com.pricing.infrastructure.database.springdata.model.jpa.price.PriceEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class PriceEntityMapperTest {

  private final PriceEntityMapper priceEntityMapper = Mappers.getMapper(PriceEntityMapper.class);


  @Test
  void givenValidPriceEntity_whenToDomain_thenReturnsPrice() {
    // Given
    BrandEntity brandEntity = new BrandEntity();
    brandEntity.setId(1L);

    PriceEntity priceEntity = new PriceEntity();
    priceEntity.setProductId(35455L);
    priceEntity.setBrand(brandEntity);
    priceEntity.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
    priceEntity.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
    priceEntity.setPriceList(1);
    priceEntity.setPrice(new BigDecimal("35.50"));

    // When
    Price result = priceEntityMapper.toDomain(priceEntity);

    // Then
    assertNotNull(result);
    assertEquals(priceEntity.getProductId(), result.getProductId());
    assertEquals(priceEntity.getBrand().getId(), result.getBrandId());
    assertEquals(priceEntity.getStartDate(), result.getStartDate());
    assertEquals(priceEntity.getEndDate(), result.getEndDate());
    assertEquals(priceEntity.getPriceList(), result.getPriceList());
    assertEquals(priceEntity.getPrice(), result.getValue());
  }

  @Test
  void givenNullPriceEntity_whenToDomain_thenReturnsNull() {

    // When
    Price result = priceEntityMapper.toDomain(null);

    // Then
    assertNull(result);
  }


}
