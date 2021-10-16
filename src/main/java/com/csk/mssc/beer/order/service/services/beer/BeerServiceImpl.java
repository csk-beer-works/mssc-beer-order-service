package com.csk.mssc.beer.order.service.services.beer;

import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.csk.mssc.beer.order.service.web.model.BeerDto;

@Service
@ConfigurationProperties(prefix = "csk.beer", ignoreUnknownFields = false)
public class BeerServiceImpl implements BeerService {

	private static final String GET_BEER_BY_ID_PATH = "/api/v1/beer/";
	private static final String GET_BEER_BY_UPC_PATH = "/api/v1/beerUpc/";
	private final RestTemplate restTemplate;

	private String beerServiceHost;

	public BeerServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public Optional<BeerDto> getBeerById(UUID id) {
		return Optional
				.of(restTemplate.getForObject(beerServiceHost + GET_BEER_BY_ID_PATH + id.toString(), BeerDto.class));
	}

	@Override
	public Optional<BeerDto> getBeerByUpc(String upc) {
		return Optional
				.of(restTemplate.getForObject(beerServiceHost + GET_BEER_BY_UPC_PATH + upc.toString(), BeerDto.class));
	}

	public void setBeerServiceHost(String beerServiceHost) {
		this.beerServiceHost = beerServiceHost;
	}
	
}
