package com.pricing.infrastructure.rest.spring.controller.price;

import com.pricing.application.usecase.price.PriceFinder;
import com.pricing.domain.exception.price.PriceFinderException;

import com.pricing.infrastructure.rest.api.price.PriceApi;
import com.pricing.infrastructure.rest.api.price.dto.PriceResponseRestDto;
import com.pricing.infrastructure.rest.spring.controller.price.mapper.PriceRestMapper;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PriceApiController implements PriceApi {

  private final PriceFinder priceFinder;
  private final PriceRestMapper priceMapper;

  @Override
  public ResponseEntity<PriceResponseRestDto> getPriceApplied(LocalDateTime date, Long productId,
      Long brandId) throws PriceFinderException {
    PriceResponseRestDto priceResponseDTO = priceFinder.getApplicablePrice(date, productId,
        brandId).map(priceMapper::toDto).orElse(new PriceResponseRestDto());

    return ResponseEntity.ok(priceResponseDTO);
  }
}
