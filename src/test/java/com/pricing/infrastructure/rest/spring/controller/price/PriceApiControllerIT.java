package com.pricing.infrastructure.rest.spring.controller.price;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pricing.infrastructure.rest.api.error.ErrorResponseWebDto;
import com.pricing.infrastructure.rest.api.price.dto.PriceResponseRestDto;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PriceApiControllerIT {

  private static final String DATE_PARAMETER = "date";
  private static final String PRODUCT_ID_PARAMETER = "productId";
  private static final Long PRODUCT_ID_DEFAULT_VALUE = 35455L;

  private static final String BRAND_ID_PARAMETER = "brandId";
  private static final Long BRAND_ID_DEFAULT_VALUE = 1L;
  private static final String LOCALHOST = "http://localhost";
  private static final String PRICING_API = "/pricing/api/v1/prices/applied";

  @LocalServerPort
  private int port;

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

  private String priceAppliedUrl;

  private RestTemplate restTemplate;


  @BeforeEach
  void setUp() {
    configureRestTemplate();

    String baseUrl = LOCALHOST.concat(":").concat(String.valueOf(port));
    priceAppliedUrl = baseUrl.concat(PRICING_API);
  }

  @Test
  @DisplayName("Test 1: Request at 10:00 on the 14th for product 35455 for brand 1 (ZARA)")
  void testGetPriceAppliedAt10On14th() {
    // Given
    URI uri = UriComponentsBuilder.fromHttpUrl(priceAppliedUrl)
        .queryParam(DATE_PARAMETER, LocalDateTime.of(2020, 6, 14, 10, 0).format(formatter))
        .queryParam(PRODUCT_ID_PARAMETER, PRODUCT_ID_DEFAULT_VALUE)
        .queryParam(BRAND_ID_PARAMETER, BRAND_ID_DEFAULT_VALUE)
        .build()
        .toUri();

    // When
    ResponseEntity<PriceResponseRestDto> response = restTemplate.exchange(uri, HttpMethod.GET,
        getDefaultEntity(), PriceResponseRestDto.class);

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(PRODUCT_ID_DEFAULT_VALUE, response.getBody().getProductId());
    assertEquals(BRAND_ID_DEFAULT_VALUE, response.getBody().getBrandId());
    assertEquals(new BigDecimal("35.50"), response.getBody().getPrice());
    assertEquals(1, response.getBody().getPriceList());
  }

  @Test
  @DisplayName("Test 2: request at 16:00 on the 14th for product 35455 for brand 1")
  void testGetPriceAppliedAt16On14th() {
    // Given
    URI uri = UriComponentsBuilder.fromHttpUrl(priceAppliedUrl)
        .queryParam(DATE_PARAMETER, LocalDateTime.of(2020, 6, 14, 16, 0).format(formatter))
        .queryParam(PRODUCT_ID_PARAMETER, PRODUCT_ID_DEFAULT_VALUE)
        .queryParam(BRAND_ID_PARAMETER, BRAND_ID_DEFAULT_VALUE)
        .build()
        .toUri();

    // When
    ResponseEntity<PriceResponseRestDto> response = restTemplate.exchange(uri, HttpMethod.GET,
        getDefaultEntity(), PriceResponseRestDto.class);

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(PRODUCT_ID_DEFAULT_VALUE, response.getBody().getProductId());
    assertEquals(BRAND_ID_DEFAULT_VALUE, response.getBody().getBrandId());
    assertEquals(new BigDecimal("25.45"), response.getBody().getPrice());
    assertEquals(2, response.getBody().getPriceList());
  }

  @Test
  @DisplayName("Test 3: request at 21:00 on the 14th for product 35455 for brand 1")
  void testGetPriceAppliedAt21On14th() {
    // Given
    URI uri = UriComponentsBuilder.fromHttpUrl(priceAppliedUrl)
        .queryParam(DATE_PARAMETER, LocalDateTime.of(2020, 6, 14, 21, 0).format(formatter))
        .queryParam(PRODUCT_ID_PARAMETER, PRODUCT_ID_DEFAULT_VALUE)
        .queryParam(BRAND_ID_PARAMETER, BRAND_ID_DEFAULT_VALUE)
        .build()
        .toUri();

    // When
    ResponseEntity<PriceResponseRestDto> response = restTemplate.exchange(uri, HttpMethod.GET,
        getDefaultEntity(), PriceResponseRestDto.class);

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(PRODUCT_ID_DEFAULT_VALUE, response.getBody().getProductId());
    assertEquals(BRAND_ID_DEFAULT_VALUE, response.getBody().getBrandId());
    assertEquals(new BigDecimal("35.50"), response.getBody().getPrice());
    assertEquals(1, response.getBody().getPriceList());
  }

  @Test
  @DisplayName("Test 4: request at 10:00 on the 15th for product 35455 for brand 1")
  void testGetPriceAppliedAt10On15th() {
    // Given
    URI uri = UriComponentsBuilder.fromHttpUrl(priceAppliedUrl)
        .queryParam(DATE_PARAMETER, LocalDateTime.of(2020, 6, 15, 10, 0).format(formatter))
        .queryParam(PRODUCT_ID_PARAMETER, PRODUCT_ID_DEFAULT_VALUE)
        .queryParam(BRAND_ID_PARAMETER, BRAND_ID_DEFAULT_VALUE)
        .build()
        .toUri();

    // When
    ResponseEntity<PriceResponseRestDto> response = restTemplate.exchange(uri, HttpMethod.GET,
        getDefaultEntity(), PriceResponseRestDto.class);

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(PRODUCT_ID_DEFAULT_VALUE, response.getBody().getProductId());
    assertEquals(BRAND_ID_DEFAULT_VALUE, response.getBody().getBrandId());
    assertEquals(new BigDecimal("30.50"), response.getBody().getPrice());
    assertEquals(3, response.getBody().getPriceList());
  }

  @Test
  @DisplayName("Test 5: request at 21:00 on the 16th for product 35455 for brand 1")
  void testGetPriceAppliedAt21On16th() {
    // Given
    URI uri = UriComponentsBuilder.fromHttpUrl(priceAppliedUrl)
        .queryParam(DATE_PARAMETER, LocalDateTime.of(2020, 6, 16, 21, 0).format(formatter))
        .queryParam(PRODUCT_ID_PARAMETER, PRODUCT_ID_DEFAULT_VALUE)
        .queryParam(BRAND_ID_PARAMETER, BRAND_ID_DEFAULT_VALUE)
        .build()
        .toUri();

    // When
    ResponseEntity<PriceResponseRestDto> response = restTemplate.exchange(uri, HttpMethod.GET,
        getDefaultEntity(), PriceResponseRestDto.class);

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(PRODUCT_ID_DEFAULT_VALUE, response.getBody().getProductId());
    assertEquals(BRAND_ID_DEFAULT_VALUE, response.getBody().getBrandId());
    assertEquals(new BigDecimal("38.95"), response.getBody().getPrice());
    assertEquals(4, response.getBody().getPriceList());
  }


  @Test
  @DisplayName("Bad Request when date parameter is missing")
  void testBadRequestWhenDateIsMissing() {
    // Given
    URI uri = UriComponentsBuilder.fromHttpUrl(priceAppliedUrl)
        .queryParam(PRODUCT_ID_PARAMETER, PRODUCT_ID_DEFAULT_VALUE)
        .queryParam(BRAND_ID_PARAMETER, BRAND_ID_DEFAULT_VALUE)
        .build()
        .toUri();

    // When
    ResponseEntity<ErrorResponseWebDto> response = restTemplate.exchange(uri, HttpMethod.GET, getDefaultEntity(), ErrorResponseWebDto.class);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  @DisplayName("Not Found when no applicable price is found")
  void testNotFoundWhenNoPriceAppliedFound() {
    // Given
    URI uri = UriComponentsBuilder.fromHttpUrl(priceAppliedUrl)
        .queryParam(DATE_PARAMETER, LocalDateTime.of(2025, 1, 1, 10, 0).format(formatter))  // A future date where no price exists
        .queryParam(PRODUCT_ID_PARAMETER, PRODUCT_ID_DEFAULT_VALUE)
        .queryParam(BRAND_ID_PARAMETER, BRAND_ID_DEFAULT_VALUE)
        .build()
        .toUri();

    // When
    ResponseEntity<ErrorResponseWebDto> response = restTemplate.exchange(uri, HttpMethod.GET, getDefaultEntity(), ErrorResponseWebDto.class);

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());  // Assert 404 status
    assertNotNull(response.getBody());  // Ensure body is not null
    assertEquals(404, response.getBody().getCode());  // Check the error code
    assertTrue(response.getBody().getMessage().contains("Not found applicable price"));  // Check error message
    assertTrue(response.getBody().getDescription().contains("Not found"));  // Check error description
  }



  private void configureRestTemplate() {
    restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
      @Override
      public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode() == HttpStatus.NOT_FOUND ||
            response.getStatusCode() == HttpStatus.BAD_REQUEST) {
          // Ignore Not Found (404) and Bad Request(400) and let the response pass through
          return;
        }
        super.handleError(response);  // For other errors, call default handler
      }
    });
  }

  private HttpEntity<?> getDefaultEntity() {
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    return new HttpEntity<>(headers);
  }
}