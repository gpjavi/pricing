package com.pricing.infrastructure.rest.api.price.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceResponseRestDto {

  private Long productId;
  private Long brandId;
  private Integer priceList;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private BigDecimal price;
}
