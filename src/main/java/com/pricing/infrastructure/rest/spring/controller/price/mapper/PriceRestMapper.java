package com.pricing.infrastructure.rest.spring.controller.price.mapper;

import com.pricing.domain.model.price.Price;
import com.pricing.infrastructure.rest.api.price.dto.PriceResponseRestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceRestMapper {

  PriceResponseRestDto toDto(Price price);
}
