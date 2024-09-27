package com.pricing.application.usecase.price;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pricing.application.usecase.price.validator.PriceFinderValidator;
import com.pricing.domain.exception.RepositoryException;
import com.pricing.domain.exception.price.PriceFinderException;
import com.pricing.domain.exception.price.PriceFinderException.PriceErrorCode;
import com.pricing.domain.model.price.Price;
import com.pricing.domain.repository.price.PriceQueryRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceFinderTest {

  @Mock
  private PriceQueryRepository queryRepository;

  @Mock
  private PriceFinderValidator priceFinderValidator;

  @InjectMocks
  private PriceFinder classUnderTest;

  private final Long productId = 35455L;
  private final Long brandId = 1L;
  private final LocalDateTime appliedDate = LocalDateTime.of(2020, 6, 14, 0, 0);

  private final Price price = new Price();

  @Test
  void givenValidParams_whenGetApplicablePrice_thenReturnsPrice()
      throws PriceFinderException, RepositoryException {
    // Given
    doNothing().when(priceFinderValidator)
        .validateGetApplicablePriceParams(appliedDate, productId, brandId);
    when(queryRepository.findFirstByDateAndProductAndBrand(appliedDate, productId, brandId))
        .thenReturn(Optional.of(price));

    // When
    Optional<Price> result = classUnderTest.getApplicablePrice(appliedDate, productId, brandId);

    // Then
    assertTrue(result.isPresent());
    assertEquals(price, result.get());
    verify(priceFinderValidator, times(1)).validateGetApplicablePriceParams(appliedDate, productId,
        brandId);
    verify(queryRepository, times(1)).findFirstByDateAndProductAndBrand(appliedDate, productId,
        brandId);
  }

  @Test
  void givenValidParams_whenGetApplicablePrice_thenReturnsEmpty()
      throws PriceFinderException, RepositoryException {
    // Given
    doNothing().when(priceFinderValidator)
        .validateGetApplicablePriceParams(appliedDate, productId, brandId);
    when(queryRepository.findFirstByDateAndProductAndBrand(appliedDate, productId, brandId))
        .thenReturn(Optional.empty());

    // When
    Optional<Price> result = classUnderTest.getApplicablePrice(appliedDate, productId, brandId);

    // Then
    assertFalse(result.isPresent());
    verify(priceFinderValidator, times(1)).validateGetApplicablePriceParams(appliedDate, productId,
        brandId);
    verify(queryRepository, times(1)).findFirstByDateAndProductAndBrand(appliedDate, productId,
        brandId);
  }

  @Test
  void givenRepositoryException_whenGetApplicablePrice_thenThrowsPriceFinderException()
      throws RepositoryException, PriceFinderException {
    // Given
    doNothing().when(priceFinderValidator)
        .validateGetApplicablePriceParams(appliedDate, productId, brandId);
    String repositoryErrorMessage = "Repository error";

    RepositoryException repositoryException = new RepositoryException(new Throwable(),
        repositoryErrorMessage);
    when(queryRepository.findFirstByDateAndProductAndBrand(appliedDate, productId, brandId))
        .thenThrow(repositoryException);

    // When & Then
    PriceFinderException priceFinderException = assertThrows(PriceFinderException.class, () ->
        classUnderTest.getApplicablePrice(appliedDate, productId, brandId));

    String expectedMessage = String.format(
        "[AppliedDate: %s][ProductId: %s][BrandId: %s] - Unable to getApplicablePrice from repository. Cause: %s",
        appliedDate, productId, brandId, repositoryErrorMessage);

    assertEquals(expectedMessage, priceFinderException.getMessage());
    assertEquals(PriceErrorCode.REPOSITORY_EXCEPTION.getErrorCode(),
        priceFinderException.getErrorCode());
    verify(priceFinderValidator, times(1)).validateGetApplicablePriceParams(appliedDate, productId,
        brandId);
    verify(queryRepository, times(1)).findFirstByDateAndProductAndBrand(appliedDate, productId,
        brandId);
  }

  @Test
  void givenInvalidParams_whenGetApplicablePrice_thenThrowsPriceFinderException()
      throws PriceFinderException, RepositoryException {
    // Given
    String validationErrorMessage = "Validation error";
    PriceFinderException validationException = new PriceFinderException(validationErrorMessage,
        PriceErrorCode.INVALID_DATA);
    doThrow(validationException).when(priceFinderValidator)
        .validateGetApplicablePriceParams(appliedDate, productId, brandId);

    // When & Then
    PriceFinderException exception = assertThrows(PriceFinderException.class, () ->
        classUnderTest.getApplicablePrice(appliedDate, productId, brandId));

    assertEquals(validationErrorMessage, exception.getMessage());
    assertEquals(PriceErrorCode.INVALID_DATA.getErrorCode(), exception.getErrorCode());
    verify(priceFinderValidator, times(1)).validateGetApplicablePriceParams(appliedDate, productId,
        brandId);
    verify(queryRepository, never()).findFirstByDateAndProductAndBrand(any(), any(), any());
  }
}