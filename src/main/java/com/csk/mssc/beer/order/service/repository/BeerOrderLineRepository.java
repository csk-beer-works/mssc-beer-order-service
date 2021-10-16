package com.csk.mssc.beer.order.service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csk.mssc.beer.order.service.domain.BeerOrderLine;

public interface BeerOrderLineRepository extends JpaRepository<BeerOrderLine, UUID> {

}
