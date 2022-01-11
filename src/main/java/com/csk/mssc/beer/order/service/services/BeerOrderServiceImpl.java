package com.csk.mssc.beer.order.service.services;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.csk.mssc.beer.order.service.domain.BeerOrder;
import com.csk.mssc.beer.order.service.domain.Customer;
import com.csk.mssc.beer.order.service.domain.BeerOrderStatusEnum;
import com.csk.mssc.beer.order.service.repository.BeerOrderRepository;
import com.csk.mssc.beer.order.service.repository.CustomerRepository;
import com.csk.mssc.beer.order.service.services.web.mapper.BeerOrderMapper;
import com.csk.mssc.beer.order.service.web.model.BeerOrderDto;
import com.csk.mssc.beer.order.service.web.model.BeerOrderPagedList;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BeerOrderServiceImpl implements BeerOrderService {

	private final BeerOrderRepository beerOrderRepository;
	private final CustomerRepository customerRepository;
	private final BeerOrderMapper beerOrderMapper;
	private final ApplicationEventPublisher publisher;

	@Override
	public BeerOrderPagedList listOrders(UUID customerId, Pageable pageable) {
		log.info("BeerOrderService ..... list orders....");
		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

		if (optionalCustomer.isPresent()) {
			Page<BeerOrder> beerOrderPage = beerOrderRepository.findAllByCustomer(optionalCustomer.get(), pageable);

			beerOrderPage.stream().forEach(b -> System.out.println(b.getCustomer().getId()));
			return new BeerOrderPagedList(
					beerOrderPage.stream().map(beerOrderMapper::beerOrderToDto).collect(Collectors.toList()), pageable,
					beerOrderPage.getTotalElements());

		}
		return null;
	}

	@Override
	@Transactional
	public BeerOrderDto placeOrder(UUID customerId, BeerOrderDto beerOrderDto) {
		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

		if (optionalCustomer.isPresent()) {
			BeerOrder beerOrder = beerOrderMapper.dtoToBeerOrder(beerOrderDto);
			beerOrder.setId(null); // should not be set by outside client
			
			beerOrder.setCustomer(optionalCustomer.get());
			beerOrder.setOrderStatus(BeerOrderStatusEnum.NEW);
			
			beerOrder.getBeerOrderLines().forEach(line -> {
				line.setBeerOrder(beerOrder);
				System.out.println(line.getUpc());
			});
			BeerOrder savedBeerOrder = beerOrderRepository.saveAndFlush(beerOrder);

            log.info("Saved Beer Order: " + beerOrder.getId());
            
            log.info("Saved Beer Order: " + beerOrder.getCustomer().getId());

            //todo impl
          //  publisher.publishEvent(new NewBeerOrderEvent(savedBeerOrder));

            return beerOrderMapper.beerOrderToDto(savedBeerOrder);

		}
		//todo add exception type
        throw new RuntimeException("Customer Not Found");
	}

	@Override
	public BeerOrderDto getOrderById(UUID customerId, UUID orderId) {
		return beerOrderMapper.beerOrderToDto(getOrder(customerId, orderId));
	}

	@Override
	public void pickupOrder(UUID customerId, UUID orderId) {
		BeerOrder beerOrder = getOrder(customerId, orderId);
        beerOrder.setOrderStatus(BeerOrderStatusEnum.PICKED_UP);

        beerOrderRepository.save(beerOrder);

	}

	private BeerOrder getOrder(UUID customerId, UUID orderId) {
		Optional<Customer> customerOptional = customerRepository.findById(customerId);

		if (customerOptional.isPresent()) {
			Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(orderId);

			if (beerOrderOptional.isPresent()) {
				BeerOrder beerOrder = beerOrderOptional.get();

				// fall to exception if customer id's do not match - order not for customer
				if (beerOrder.getCustomer().getId().equals(customerId)) {
					return beerOrder;
				}
			}
			throw new RuntimeException("Beer Order Not Found");
		}
		throw new RuntimeException("Customer Not Found");
	}

}
