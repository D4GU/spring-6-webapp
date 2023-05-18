package com.springframework.spring6restmvc.repositories;

import com.springframework.spring6restmvc.bootstrap.BootstrapData;
import com.springframework.spring6restmvc.entities.Beer;
import com.springframework.spring6restmvc.model.BeerStyle;
import com.springframework.spring6restmvc.services.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;



    @Test
    void testSaveBeerTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            Beer savedBeer = beerRepository.save(Beer.builder()
                    .beerName("My Beer 1233333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("123123")
                    .price(new BigDecimal(30))
                    .build());
            beerRepository.flush();
        });
    }
    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("My Beer")
                .beerStyle(BeerStyle.LAGER)
                .upc("123123")
                .price(new BigDecimal(30))
                .build());

        beerRepository.flush();
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();

    }

    @Test
    void testGetBeerByName() {
        Page<Beer> page = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%", null);
        assertThat(page.getContent().size()).isEqualTo(336);
    }

    @Test
    void testGetBeerByStyle() {
        Page<Beer> page = beerRepository.findAllByBeerStyle(BeerStyle.IPA, null);
        assertThat(page.getContent().size()).isEqualTo(547);
    }

    @Test
    void testGetBeerByStyleAndName() {
        Page<Beer> page = beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%IPA%",BeerStyle.IPA, null);
        assertThat(page.getContent().size()).isEqualTo(310);
    }
}