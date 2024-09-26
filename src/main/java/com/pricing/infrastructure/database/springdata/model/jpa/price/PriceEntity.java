package com.pricing.infrastructure.database.springdata.model.jpa.price;

import com.pricing.infrastructure.database.springdata.model.jpa.brand.BrandEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prices")
public class PriceEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "brand_id", nullable = false)
  private BrandEntity brand;  // FK to BrandEntity

  @Column(name = "start_date", nullable = false)
  private LocalDateTime startDate;

  @Column(name = "end_date", nullable = false)
  private LocalDateTime endDate;

  @Column(name = "price_list", nullable = false)
  private Integer priceList;

  @Column(name = "product_id", nullable = false)
  private Long productId;

  @Column(name = "priority", nullable = false)
  private Integer priority;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Enumerated(EnumType.STRING)
  @Column(name = "currency", nullable = false, length = 3)
  private CurrencyRepositoryDto currency;
}
