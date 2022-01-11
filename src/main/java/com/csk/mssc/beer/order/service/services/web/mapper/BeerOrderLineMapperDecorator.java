package com.csk.mssc.beer.order.service.services.web.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.csk.mssc.beer.order.service.domain.BeerOrderLine;
import com.csk.mssc.beer.order.service.services.beer.BeerService;
import com.csk.brewery.model.BeerOrderLineDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BeerOrderLineMapperDecorator implements BeerOrderLineMapper {
	
	private BeerService beerService;
	private BeerOrderLineMapper beerOrderLineMapper;
	
	
    @Autowired
	public void setBeerService(BeerService beerService) {
		this.beerService = beerService;
	}

    @Autowired
    @Qualifier("delegate")
	public void setBeerOrderLineMapper(BeerOrderLineMapper beerOrderLineMapper) {
		this.beerOrderLineMapper = beerOrderLineMapper;
	}

	@Override
	public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line) {
		
		BeerOrderLineDto beerOrderLineDto = beerOrderLineMapper.beerOrderLineToDto(line);
		beerService.getBeerByUpc(line.getUpc()).ifPresent(beerDto -> {
			beerOrderLineDto.setBeerId(beerDto.getId());
			beerOrderLineDto.setBeerName(beerDto.getBeerName());
			beerOrderLineDto.setBeerStyle(beerDto.getBeerStyle());
			beerOrderLineDto.setPrice(beerDto.getPrice());
		});
		
		
		return beerOrderLineDto;
	}

}
