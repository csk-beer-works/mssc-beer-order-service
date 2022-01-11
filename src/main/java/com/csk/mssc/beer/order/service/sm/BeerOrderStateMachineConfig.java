package com.csk.mssc.beer.order.service.sm;

import static com.csk.mssc.beer.order.service.domain.BeerOrderStatusEnum.*;
import static com.csk.mssc.beer.order.service.domain.BeerOrderEventEnum.*;

import com.csk.mssc.beer.order.service.domain.BeerOrderEventEnum;
import com.csk.mssc.beer.order.service.domain.BeerOrderStatusEnum;

import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

public class BeerOrderStateMachineConfig extends StateMachineConfigurerAdapter<BeerOrderStatusEnum, BeerOrderEventEnum> {

    @Override
    public void configure(StateMachineStateConfigurer<BeerOrderStatusEnum, BeerOrderEventEnum> states) throws Exception {
        states.withStates()
                .initial(NEW)
                .states(EnumSet.allOf(BeerOrderStatusEnum.class))
                .end(PICKED_UP)
                .end(DELIVERED)
                .end(VALIDATION_EXCEPTION)
                .end(ALLOCATION_EXCEPTION)
                .end(DELIVERY_EXCEPTION);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<BeerOrderStatusEnum, BeerOrderEventEnum> transitions) throws Exception {
        transitions.withExternal()
                .source(NEW).target(NEW).event(VALIDATE_ORDER)
                .and()
                .withExternal().source(NEW).target(VALIDATED).event(VALIDATION_SUCCESS)
                .and()
                .withExternal().source(NEW).target(VALIDATION_EXCEPTION).event(VALIDATION_FAILED);

               /* .and()
                .withExternal().source(VALIDATED).target(ALLOCATED).event(ALLOCATION_SUCCESS)
                .and()
                .withExternal().source(VALIDATED).target(ALLOCATION_EXCEPTION).event(ALLOCATION_FAILED)
                .and()
                .withExternal().source(ALLOCATED).target(DELIVERED).event(BEER_ORDER_PICKED_UP)
                .and()
                .withExternal().source(ALLOCATED).target(DELIVERY_EXCEPTION).event(DELI) */

    }
}
