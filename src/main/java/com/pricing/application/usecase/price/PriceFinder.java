package com.pricing.application.usecase.price;

import com.pricing.application.usecase.price.validator.PriceFinderValidator;
import com.pricing.domain.exception.RepositoryException;
import com.pricing.domain.exception.price.PriceFinderException;
import com.pricing.domain.exception.price.PriceFinderException.PriceErrorCode;
import com.pricing.domain.model.price.Price;
import com.pricing.domain.repository.price.PriceQueryRepository;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PriceFinder {

  private final PriceQueryRepository queryRepository;
  private final PriceFinderValidator priceFinderValidator;

  public Optional<Price> getApplicablePrice(@NotNull LocalDateTime appliedDate,
      @NotNull Long productId, @NotNull Long brandId) throws PriceFinderException {

    priceFinderValidator.validateGetApplicablePriceParams(appliedDate, productId, brandId);
    try {
      return queryRepository.findFirstByDateAndProductAndBrand(appliedDate, productId,
          brandId);
    } catch (RepositoryException repositoryException) {
      String errorMessage = String.format(
          "[AppliedDate: %s][ProductId: %s][BrandId: %s] - Unable to getApplicablePrice from repository. Cause: %s",
          appliedDate, productId, brandId, repositoryException.getLocalizedMessage());
      throw new PriceFinderException(repositoryException, errorMessage,
          PriceErrorCode.REPOSITORY_EXCEPTION);
    }
  }
}
