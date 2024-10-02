package com.pricing.infrastructure.rest.spring.controller.price;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import com.pricing.application.usecase.price.PriceFinder;
import com.pricing.domain.exception.price.PriceFinderException;
import com.pricing.domain.exception.price.PriceFinderException.PriceErrorCode;
import com.pricing.domain.model.price.Price;
import com.pricing.infrastructure.rest.api.price.dto.PriceResponseRestDto;
import com.pricing.infrastructure.rest.spring.controller.price.mapper.PriceRestMapper;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class PriceApiControllerTest {

  private static final String ERROR_MESSAGE = "Some error message";

  @InjectMocks
  private PriceApiController classUnderTest;

  @Mock
  private PriceFinder priceFinder;

  @Mock
  private PriceRestMapper priceMapper;

  private final Long productId = 35455L;
  private final Long brandId = 1L;
  private final LocalDateTime date = LocalDateTime.of(2020, 6, 14, 0, 0);
  private final Price price = new Price();
  private final PriceResponseRestDto priceResponseRestDto = new PriceResponseRestDto();


  @Test
  @DisplayName("getPriceApplied returns valid price for given parameters")
  void givenValidParams_whenGetPriceApplied_thenReturnsPriceResponse() throws PriceFinderException {
    // Given
    when(priceFinder.getApplicablePrice(date, productId, brandId)).thenReturn(Optional.of(price));
    when(priceMapper.toDto(price)).thenReturn(priceResponseRestDto);

    // When
    ResponseEntity<PriceResponseRestDto> response =
        classUnderTest.getPriceApplied(date, productId, brandId);

    // Then
    assertEquals(OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(priceResponseRestDto, response.getBody());
    verify(priceFinder, times(1)).getApplicablePrice(date, productId, brandId);
    verify(priceMapper, times(1)).toDto(price);
  }

  @Test
  @DisplayName("getPriceApplied throws PriceFinderException when no price is found")
  void testGetPriceAppliedThrowsNotFoundException() throws PriceFinderException {
    // Given
    when(priceFinder.getApplicablePrice(date, productId, brandId)).thenReturn(Optional.empty());

    // When & Then
    PriceFinderException exception = assertThrows(PriceFinderException.class, () ->
        classUnderTest.getPriceApplied(date, productId, brandId));

    assertTrue(exception.getMessage().contains("Not found applicable price"));
    assertEquals(PriceErrorCode.NOT_FOUND, exception.getPriceErrorCode());
    verify(priceFinder, times(1)).getApplicablePrice(date, productId, brandId);
    verify(priceMapper, never()).toDto(any());
  }

  @Test
  void givenExceptionThrown_whenGetPriceApplied_thenThrowsPriceFinderException()
      throws PriceFinderException {
    // Given
    when(priceFinder.getApplicablePrice(date, productId, brandId)).thenThrow(
        new PriceFinderException(ERROR_MESSAGE,
            PriceFinderException.PriceErrorCode.REPOSITORY_EXCEPTION));

    // When & Then
    PriceFinderException exception = assertThrows(PriceFinderException.class, () ->
        classUnderTest.getPriceApplied(date, productId, brandId));

    assertEquals(ERROR_MESSAGE, exception.getMessage());
    verify(priceFinder, times(1)).getApplicablePrice(date, productId, brandId);
    verify(priceMapper, never()).toDto(any());
  }
}