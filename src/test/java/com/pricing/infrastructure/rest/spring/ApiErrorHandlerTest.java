package com.pricing.infrastructure.rest.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

import com.pricing.domain.exception.ErrorCode;
import com.pricing.domain.exception.price.PriceFinderException;
import com.pricing.domain.exception.price.PriceFinderException.PriceErrorCode;
import com.pricing.infrastructure.rest.api.error.ErrorResponseWebDto;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ApiErrorHandlerTest {

  @InjectMocks
  private ApiErrorHandler apiErrorHandler;

  private HttpServletRequest mockRequest;

  @BeforeEach
  void setUp() {
    mockRequest = mock(HttpServletRequest.class);
  }

  private static final String ERROR_MESSAGE = "some error message";

  @Test
  @DisplayName("handleException for generic Exception")
  void givenGenericException_whenHandleException_thenReturnsInternalServerError() {
    // Given
    Exception exception = new Exception(ERROR_MESSAGE);

    // When
    ResponseEntity<ErrorResponseWebDto> response = apiErrorHandler.handleException(exception,
        mockRequest);

    // Then
    assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(ERROR_MESSAGE, response.getBody().getMessage());
  }

  @Test
  @DisplayName("handleDomainException for repository error")
  void givenPriceFinderExceptionWithRepositoryType_whenHandlePriceFinderException_thenReturnsServiceUnavailable() {
    // Given
    PriceErrorCode priceErrorCode = PriceErrorCode.REPOSITORY_EXCEPTION;
    PriceFinderException priceFinderException = new PriceFinderException(ERROR_MESSAGE,
        priceErrorCode);

    // When
    ResponseEntity<ErrorResponseWebDto> response = apiErrorHandler.handleDomainException(
        priceFinderException);

    // Then

    assertEquals(SERVICE_UNAVAILABLE, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(ERROR_MESSAGE, response.getBody().getMessage());

    ErrorCode errorCode = priceErrorCode.getErrorCode();
    assertEquals(errorCode.getId(), response.getBody().getCode());
    assertEquals(errorCode.getMessage(), response.getBody().getDescription());
  }


  @Test
  @DisplayName("handleDomainException for invalid data")
  void givenPriceFinderExceptionWithInvalidDataType_whenHandlePriceFinderException_thenReturnsBadRequest() {
    // Given

    PriceErrorCode priceErrorCode = PriceErrorCode.INVALID_DATA;
    PriceFinderException priceFinderException = new PriceFinderException(ERROR_MESSAGE,
        priceErrorCode);

    // When
    ResponseEntity<ErrorResponseWebDto> response = apiErrorHandler.handleDomainException(
        priceFinderException);

    // Then
    assertEquals(BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(ERROR_MESSAGE, response.getBody().getMessage());

    ErrorCode errorCode = priceErrorCode.getErrorCode();
    assertEquals(errorCode.getId(), response.getBody().getCode());
    assertEquals(errorCode.getMessage(), response.getBody().getDescription());
  }

  @Test
  @DisplayName("handleDomainException for unknown exception type (default case)")
  void givenPriceFinderExceptionWithUnhandledExceptionType_whenHandlePriceFinderException_thenReturnsInternalServerError() {
    // Given

    PriceErrorCode priceErrorCode = PriceErrorCode.UNKNOWN_EXCEPTION;

    PriceFinderException priceFinderException = new PriceFinderException(ERROR_MESSAGE);

    // When
    ResponseEntity<ErrorResponseWebDto> response = apiErrorHandler.handleDomainException(
        priceFinderException);

    // Then
    assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(ERROR_MESSAGE, response.getBody().getMessage());

    ErrorCode errorCode = priceErrorCode.getErrorCode();
    assertEquals(errorCode.getId(), response.getBody().getCode());
    assertEquals(errorCode.getMessage(), response.getBody().getDescription());
  }

  @Test
  @DisplayName("handleDomainException for not found exception type")
  void givenPriceFinderExceptionWithNotFoundExceptionType_whenHandlePriceFinderException_thenReturnsNotFound() {
    // Given

    PriceErrorCode priceErrorCode = PriceErrorCode.NOT_FOUND;

    PriceFinderException priceFinderException = new PriceFinderException(ERROR_MESSAGE, PriceErrorCode.NOT_FOUND);

    // When
    ResponseEntity<ErrorResponseWebDto> response = apiErrorHandler.handleDomainException(
        priceFinderException);

    // Then
    assertEquals(NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(ERROR_MESSAGE, response.getBody().getMessage());

    ErrorCode errorCode = priceErrorCode.getErrorCode();
    assertEquals(errorCode.getId(), response.getBody().getCode());
    assertEquals(errorCode.getMessage(), response.getBody().getDescription());
  }


}