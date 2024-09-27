package com.pricing.infrastructure.database.springdata.repository.price;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pricing.domain.exception.RepositoryException;
import com.pricing.domain.model.price.Price;
import com.pricing.infrastructure.database.springdata.mapper.PriceEntityMapper;
import com.pricing.infrastructure.database.springdata.model.jpa.price.PriceEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceQueryRepositoryImplTest {

  @Mock
  private PriceEntityJpaRepository jpaRepository;

  @Mock
  private PriceEntityMapper priceEntityMapper;

  @InjectMocks
  private PriceQueryRepositoryImpl classUnderTest;

  private final Long productId = 35455L;
  private final Long brandId = 1L;
  private final LocalDateTime appliedDate = LocalDateTime.of(2020, 6, 14, 0, 0);

  @Test
  void givenValidParams_whenFindFirstByDateAndProductAndBrand_thenReturnsPrice()
      throws RepositoryException {
    // Given
    PriceEntity priceEntity = new PriceEntity();
    Price price = new Price();

    when(
        jpaRepository.findTop1ByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            productId, brandId, appliedDate, appliedDate)).thenReturn(Optional.of(priceEntity));
    when(priceEntityMapper.toDomain(priceEntity)).thenReturn(price);

    // When
    Optional<Price> result = classUnderTest.findFirstByDateAndProductAndBrand(appliedDate,
        productId, brandId);

    // Then
    assertTrue(result.isPresent());
    assertEquals(price, result.get());
    verify(jpaRepository, times(
        1)).findTop1ByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
        productId, brandId, appliedDate, appliedDate);
    verify(priceEntityMapper, times(1)).toDomain(priceEntity);
  }

  @Test
  void givenNoPriceFound_whenFindFirstByDateAndProductAndBrand_thenReturnsEmpty()
      throws RepositoryException {
    // Given
    when(
        jpaRepository.findTop1ByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            productId, brandId, appliedDate, appliedDate)).thenReturn(Optional.empty());

    // When
    Optional<Price> result = classUnderTest.findFirstByDateAndProductAndBrand(appliedDate,
        productId, brandId);

    // Then
    assertFalse(result.isPresent());
    verify(jpaRepository, times(
        1)).findTop1ByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
        productId, brandId, appliedDate, appliedDate);
    verify(priceEntityMapper, never()).toDomain(any());
  }

  @Test
  void givenExceptionThrown_whenFindFirstByDateAndProductAndBrand_thenThrowsRepositoryException() {
    // Given
    String errorMessage = String.format(
        "[AppliedDate: %s][ProductId: %s][BrandId: %s] - Unable to perform findFirstByDateAndProductAndBrand operation.",
        appliedDate, productId, brandId);

    when(
        jpaRepository.findTop1ByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            productId, brandId, appliedDate, appliedDate)).thenThrow(
        new RuntimeException("DB Error"));

    // When & Then
    RepositoryException exception = assertThrows(RepositoryException.class, () ->
        classUnderTest.findFirstByDateAndProductAndBrand(appliedDate, productId, brandId)
    );

    assertEquals(errorMessage, exception.getMessage());
    verify(jpaRepository, times(
        1)).findTop1ByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
        productId, brandId, appliedDate, appliedDate);
    verify(priceEntityMapper, never()).toDomain(any());
  }
}