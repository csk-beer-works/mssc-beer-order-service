package com.csk.mssc.beer.order.service.services.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.csk.mssc.beer.order.service.domain.BeerOrder;
import com.csk.mssc.beer.order.service.web.model.BeerOrderDto;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {

	@Mapping(target = "customerId", source = "customer.id")
	BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

	BeerOrder dtoToBeerOrder(BeerOrderDto dto);
}
