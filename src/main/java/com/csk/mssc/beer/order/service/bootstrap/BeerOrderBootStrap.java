package com.csk.mssc.beer.order.service.bootstrap;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.csk.mssc.beer.order.service.domain.Customer;
import com.csk.mssc.beer.order.service.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class BeerOrderBootStrap implements CommandLineRunner {
	
	public static final String TASTING_ROOM = "Tasting Room";
    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";
	
	private final CustomerRepository customerRepo;

	@Override
	public void run(String... args) throws Exception {
		loadCustomerData();
	}
	
	private void loadCustomerData() {
		if (customerRepo.count() == 0) {
			Customer savedCustomer = customerRepo.save(Customer.builder()
									  .customerName(TASTING_ROOM)
									  .apiKey(UUID.randomUUID())
									  .build());
			
			log.debug("Tasting Room Customer Id = " + savedCustomer.getId().toString());
			
			log.info("Tasting Room Customer Id = " + savedCustomer.getId().toString());
		}
	}

}
