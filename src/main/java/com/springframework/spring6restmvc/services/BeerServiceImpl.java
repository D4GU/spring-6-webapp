package com.springframework.spring6restmvc.services;

import com.springframework.spring6restmvc.model.BeerDTO;
import com.springframework.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        BeerDTO beer1 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Test Beer")
                .beerStyle(BeerStyle.Lager)
                .upc("4213")
                .price(new BigDecimal("14.2"))
                .quantityOnHand(312)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        BeerDTO beer2 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Test Beer 2")
                .beerStyle(BeerStyle.Felschlösschen)
                .upc("4213")
                .price(new BigDecimal("14.2"))
                .quantityOnHand(312)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(),beer1);
        beerMap.put(beer2.getId(),beer2);
    }

    @Override
    public List<BeerDTO> listBeer(){
        return new ArrayList<>(beerMap.values());
    }
    @Override
    public Optional<BeerDTO> getBeerByID(UUID id) {
        log.debug("Get Beer Id in service was called, Id:" + id.toString());
        return Optional.of(beerMap.get(id));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        BeerDTO savedBeer = BeerDTO.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .quantityOnHand(beer.getQuantityOnHand())
                .version(beer.getVersion())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .build();

        beerMap.put(savedBeer.getId(), savedBeer);
        return savedBeer;
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
        BeerDTO tempBeer = beerMap.get(beerId);
        tempBeer.setBeerName(beer.getBeerName());
        tempBeer.setBeerStyle(beer.getBeerStyle());
        tempBeer.setPrice(beer.getPrice());
        tempBeer.setUpc(beer.getUpc());
        tempBeer.setUpdatedDate(LocalDateTime.now());
        tempBeer.setQuantityOnHand(beer.getQuantityOnHand());

        beerMap.put(tempBeer.getId(),tempBeer);
        return Optional.of(tempBeer);
    }

    @Override
    public Boolean deleteBeer(UUID beerId) {
        beerMap.remove(beerId);
        return true;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
        BeerDTO tempBeer = beerMap.get(beerId);
        if (StringUtils.hasText(beer.getBeerName())){
            tempBeer.setBeerName(beer.getBeerName());
        }

        if (beer.getBeerStyle() != null){
            tempBeer.setBeerStyle(beer.getBeerStyle());
        }

        if(beer.getPrice() != null){
            tempBeer.setPrice(beer.getPrice());
        }

        if(beer.getQuantityOnHand() != null){
            tempBeer.setQuantityOnHand(beer.getQuantityOnHand());
        }

        if(StringUtils.hasText(beer.getUpc())) {
            tempBeer.setUpc(beer.getUpc());
        }
        beerMap.put(beerId, tempBeer);
        return Optional.of(tempBeer);
    }
}
