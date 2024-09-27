package com.pricing.infrastructure.rest.spring.controller.price;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import com.pricing.application.usecase.price.PriceFinder;
import com.pricing.domain.exception.price.PriceFinderException;
import com.pricing.domain.model.price.Price;
import com.pricing.infrastructure.rest.api.price.dto.PriceResponseRestDto;
import com.pricing.infrastructure.rest.spring.controller.price.mapper.PriceRestMapper;
import java.time.LocalDateTime;
import java.util.Optional;
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
  private final PriceResponseRestDto PriceResponseRestDto = new PriceResponseRestDto();


  @Test
  void givenValidParams_whenGetPriceApplied_thenReturnsPriceResponse() throws PriceFinderException {
    // Given
    when(priceFinder.getApplicablePrice(date, productId, brandId)).thenReturn(Optional.of(price));
    when(priceMapper.toDto(price)).thenReturn(PriceResponseRestDto);

    // When
    ResponseEntity<PriceResponseRestDto> response =
        classUnderTest.getPriceApplied(date, productId, brandId);

    // Then
    assertEquals(OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(PriceResponseRestDto, response.getBody());
    verify(priceFinder, times(1)).getApplicablePrice(date, productId, brandId);
    verify(priceMapper, times(1)).toDto(price);
  }

  @Test
  void givenNoPriceFound_whenGetPriceApplied_thenReturnsEmptyResponse()
      throws PriceFinderException {
    // Given
    when(priceFinder.getApplicablePrice(date, productId, brandId)).thenReturn(Optional.empty());

    // When
    ResponseEntity<PriceResponseRestDto> response =
        classUnderTest.getPriceApplied(date, productId, brandId);

    // Then
    assertEquals(OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(new PriceResponseRestDto(), response.getBody());
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