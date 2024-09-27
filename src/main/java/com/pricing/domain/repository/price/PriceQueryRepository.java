package com.pricing.domain.repository.price;

import com.pricing.domain.exception.RepositoryException;
import com.pricing.domain.model.price.Price;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceQueryRepository {

  Optional<Price> findFirstByDateAndProductAndBrand(LocalDateTime date, Long productId,
      Long brandId) throws RepositoryException;
}
