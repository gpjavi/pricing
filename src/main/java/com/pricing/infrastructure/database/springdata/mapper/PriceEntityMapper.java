package com.pricing.infrastructure.database.springdata.mapper;

import com.pricing.domain.model.price.Price;
import com.pricing.infrastructure.database.springdata.model.jpa.price.PriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceEntityMapper {


  @Mapping(source = "brand.id", target = "brandId")
  Price toDomain(PriceEntity entity);

}
