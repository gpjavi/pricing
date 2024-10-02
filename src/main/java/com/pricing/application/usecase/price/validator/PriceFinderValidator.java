package com.pricing.application.usecase.price.validator;

import static java.util.Objects.isNull;

import com.pricing.domain.exception.price.PriceFinderException;
import com.pricing.domain.exception.price.PriceFinderException.PriceErrorCode;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PriceFinderValidator {

  private static final String NULL_APPLIED_DATA_MESSAGE = "AppliedDate must be provided";
  private static final String NULL_PRODUCT_ID_MESSAGE = "ProductId must be provided";
  private static final String NULL_BRAND_ID_MESSAGE = "BrandId must be provided";

  public void validateGetApplicablePriceParams(LocalDateTime appliedDate,
      Long productId, Long brandId) throws PriceFinderException {

    if (isNull(appliedDate)) {
      throw new PriceFinderException(NULL_APPLIED_DATA_MESSAGE, PriceErrorCode.INVALID_DATA);
    }

    if (isNull(productId)) {
      throw new PriceFinderException(NULL_PRODUCT_ID_MESSAGE, PriceErrorCode.INVALID_DATA);
    }

    if (isNull(brandId)) {
      throw new PriceFinderException(NULL_BRAND_ID_MESSAGE, PriceErrorCode.INVALID_DATA);
    }

  }
}
