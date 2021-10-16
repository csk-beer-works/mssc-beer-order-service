package com.csk.mssc.beer.order.service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csk.mssc.beer.order.service.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID>{

	List<Customer> findAllByCustomerNameLike(String tastingRoom);

}
