package com.csk.mssc.beer.order.service.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.csk.mssc.beer.order.service.domain.BeerOrder;
import com.csk.mssc.beer.order.service.domain.Customer;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID>{
	
	Page<BeerOrder> findAllByCustomer(Customer customer, Pageable pageable);

}
