package com.csk.mssc.beer.order.service.services.web.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import com.csk.mssc.beer.order.service.domain.BeerOrderLine;
import com.csk.brewery.model.BeerOrderLineDto;

@Mapper(uses = DateMapper.class)
@DecoratedWith(BeerOrderLineMapperDecorator.class)
public interface BeerOrderLineMapper {

	BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line);

	BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto);

}
