package com.csk.mssc.beer.order.service.services.beer;

import java.util.Optional;
import java.util.UUID;

import com.csk.brewery.model.BeerDto;

public interface BeerService {
	
	Optional<BeerDto> getBeerById(UUID id);
	
	Optional<BeerDto> getBeerByUpc(String upc);

}
