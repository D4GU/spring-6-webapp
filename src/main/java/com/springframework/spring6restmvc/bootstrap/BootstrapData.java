package com.springframework.spring6restmvc.bootstrap;

import com.springframework.spring6restmvc.entities.Beer;
import com.springframework.spring6restmvc.entities.Customer;
import com.springframework.spring6restmvc.model.BeerStyle;
import com.springframework.spring6restmvc.repositories.BeerRepository;
import com.springframework.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();

    }

    private void loadBeerData() {
        if(beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("Test Beer")
                    .beerStyle(BeerStyle.Lager)
                    .upc("4213")
                    .price(new BigDecimal("14.2"))
                    .quantityOnHand(312)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Test Beer 2")
                    .beerStyle(BeerStyle.Felschlösschen)
                    .upc("4213")
                    .price(new BigDecimal("14.2"))
                    .quantityOnHand(312)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            beerRepository.saveAll(Arrays.asList(beer1, beer2));
        }


    }
    private void loadCustomerData() {
        if(customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .id(UUID.randomUUID())
                    .customerName("Daniel Guggisberg")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .id(UUID.randomUUID())
                    .customerName("Joel Guggisberg")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1, customer2));
        }
    }


}
