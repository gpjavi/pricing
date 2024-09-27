package com.pricing.infrastructure.rest.spring.controller.price.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.pricing.domain.model.price.Price;
import com.pricing.infrastructure.rest.api.price.dto.PriceResponseRestDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class PriceRestMapperTest {

  private final PriceRestMapper classUnderTest = Mappers.getMapper(PriceRestMapper.class);

  @Test
  void givenValidPrice_whenToDto_thenReturnsPriceResponseRestDto() {
    // Given
    Price price = new Price();
    price.setProductId(35455L);
    price.setBrandId(1L);
    price.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
    price.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
    price.setPriceList(1);
    price.setPrice(new BigDecimal("35.50"));

    // When
    PriceResponseRestDto result = classUnderTest.toDto(price);

    // Then
    assertNotNull(result);
    assertEquals(price.getProductId(), result.getProductId());
    assertEquals(price.getBrandId(), result.getBrandId());
    assertEquals(price.getStartDate(), result.getStartDate());
    assertEquals(price.getEndDate(), result.getEndDate());
    assertEquals(price.getPriceList(), result.getPriceList());
    assertEquals(price.getPrice(), result.getPrice());
  }

  @Test
  void givenNullPrice_whenToDto_thenReturnsNull() {
    
    // When
    PriceResponseRestDto result = classUnderTest.toDto(null);

    // Then
    assertNull(result);
  }

}
