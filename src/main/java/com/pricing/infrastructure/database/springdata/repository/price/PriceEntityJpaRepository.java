package com.pricing.infrastructure.database.springdata.repository.price;

import com.pricing.infrastructure.database.springdata.model.jpa.price.PriceEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceEntityJpaRepository extends CrudRepository<PriceEntity, Long> {

  Optional<PriceEntity> findTop1ByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
      Long productId, Long brandId, LocalDateTime startDate, LocalDateTime endDate);
}
