package com.pricing.application.usecase.price.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.pricing.domain.exception.price.PriceFinderException;
import com.pricing.domain.exception.price.PriceFinderException.PriceErrorCode;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;


class PriceFinderValidatorTest {

  private final PriceFinderValidator classUnderTest = new PriceFinderValidator();

  @Test
  void givenValidParams_whenValidateGetApplicablePriceParams_thenNoExceptionThrown() {
    // Given
    LocalDateTime appliedDate = LocalDateTime.now();
    Long productId = 35455L;
    Long brandId = 1L;

    // When & Then
    assertDoesNotThrow(
        () -> classUnderTest.validateGetApplicablePriceParams(appliedDate, productId,
            brandId));
  }

  @Test
  void givenNullAppliedDate_whenValidateGetApplicablePriceParams_thenThrowsPriceFinderException() {
    // Given
    Long productId = 35455L;
    Long brandId = 1L;

    // When & Then
    PriceFinderException exception = assertThrows(PriceFinderException.class, () ->
        classUnderTest.validateGetApplicablePriceParams(null, productId, brandId));

    assertEquals("AppliedDate must be provided", exception.getMessage());
    assertEquals(PriceErrorCode.INVALID_DATA.getErrorCode(), exception.getErrorCode());
  }

  @Test
  void givenNullProductId_whenValidateGetApplicablePriceParams_thenThrowsPriceFinderException() {
    // Given
    LocalDateTime appliedDate = LocalDateTime.now();
    Long brandId = 1L;

    // When & Then
    PriceFinderException exception = assertThrows(PriceFinderException.class, () ->
        classUnderTest.validateGetApplicablePriceParams(appliedDate, null, brandId));

    assertEquals("ProductId must be provided", exception.getMessage());
    assertEquals(PriceErrorCode.INVALID_DATA.getErrorCode(), exception.getErrorCode());
  }

  @Test
  void givenNullBrandId_whenValidateGetApplicablePriceParams_thenThrowsPriceFinderException() {
    // Given
    LocalDateTime appliedDate = LocalDateTime.now();
    Long productId = 35455L;

    // When & Then
    PriceFinderException exception = assertThrows(PriceFinderException.class, () ->
        classUnderTest.validateGetApplicablePriceParams(appliedDate, productId, null));

    assertEquals("BrandId must be provided", exception.getMessage());
    assertEquals(PriceErrorCode.INVALID_DATA.getErrorCode(), exception.getErrorCode());
  }
}