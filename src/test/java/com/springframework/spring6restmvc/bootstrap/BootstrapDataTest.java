package com.springframework.spring6restmvc.bootstrap;

import com.springframework.spring6restmvc.repositories.BeerRepository;
import com.springframework.spring6restmvc.repositories.CustomerRepository;
import com.springframework.spring6restmvc.services.BeerCsvService;
import com.springframework.spring6restmvc.services.BeerCsvServiceImpl;
import com.springframework.spring6restmvc.services.BeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BeerCsvServiceImpl.class)
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerCsvService beerCsvService;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(beerRepository, customerRepository, beerCsvService);
    }

    @Test
    void run() throws Exception {
        bootstrapData.run(null);

        assertThat(beerRepository.count()).isEqualTo(2412);
        assertThat(customerRepository.count()).isEqualTo(2);
    }
}