package com.springframework.spring6restmvc.repositories;

import com.springframework.spring6restmvc.entities.Beer;
import com.springframework.spring6restmvc.entities.BeerOrder;
import com.springframework.spring6restmvc.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    Customer customer;
    Beer beer;

    @BeforeEach
    void setUp() {
        customer = customerRepository.findAll().get(0);
        beer = beerRepository.findAll().get(0);
    }

    @Transactional
    @Test
    void testBeerOrder() {
        BeerOrder beerOrder = BeerOrder.builder()
                .customerRef("Testing Order")
                .customer(customer)
                .build();

        BeerOrder savedBeerOrder = beerOrderRepository.saveAndFlush(beerOrder);

        System.out.println(savedBeerOrder.getCustomerRef());
    }

    @Test
    void testBeerOrders() {
        System.out.println(beerOrderRepository.count());
        System.out.println(customerRepository.count());
        System.out.println(beerRepository.count());
        System.out.println(customer.getCustomerName());
        System.out.println(beer.getBeerName());
    }
}