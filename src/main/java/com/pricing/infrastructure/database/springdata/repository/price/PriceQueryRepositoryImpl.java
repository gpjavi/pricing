package com.pricing.infrastructure.database.springdata.repository.price;

import com.pricing.domain.exception.RepositoryException;
import com.pricing.domain.model.price.Price;
import com.pricing.domain.repository.price.PriceQueryRepository;
import com.pricing.infrastructure.database.springdata.mapper.PriceEntityMapper;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class PriceQueryRepositoryImpl implements PriceQueryRepository {

  private final PriceEntityJpaRepository jpaRepository;
  private final PriceEntityMapper priceEntityMapper;

  @Override
  public Optional<Price> findFirstByDateAndProductAndBrand(@NotNull LocalDateTime appliedDate,
      @NotNull Long productId, @NotNull Long brandId) throws RepositoryException {

    try {
      return jpaRepository.findTop1ByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
          productId, brandId, appliedDate, appliedDate).map(priceEntityMapper::toDomain);
    } catch (Exception exception) {
      String errorMessage = String.format(
          "[AppliedDate: %s][ProductId: %s][BrandId: %s] - Unable to perform findFirstByDateAndProductAndBrand operation.",
          appliedDate, productId, brandId);
      throw new RepositoryException(exception, errorMessage);
    }
  }
}
