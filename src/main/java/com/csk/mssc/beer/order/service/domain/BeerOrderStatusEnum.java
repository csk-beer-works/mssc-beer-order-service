package com.csk.mssc.beer.order.service.domain;

public enum BeerOrderStatusEnum {
	
	NEW, VALIDATED, VALIDATION_EXCEPTION,
	ALLOCATED, ALLOCATION_EXCEPTION,
	PENDING_INVENTORY,
	DELIVERED, DELIVERY_EXCEPTION,
	PICKED_UP;

}