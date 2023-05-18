package com.springframework.spring6restmvc.services;

import com.springframework.spring6restmvc.entities.Beer;
import com.springframework.spring6restmvc.mappers.BeerMapper;
import com.springframework.spring6restmvc.model.BeerDTO;
import com.springframework.spring6restmvc.model.BeerStyle;
import com.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_PAGE_SIZE = 25;

    @Override
    public Page<BeerDTO> listBeer(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);


        Page<Beer> beerPage;

        if(StringUtils.hasText(beerName) && beerStyle == null) {
            beerPage = listBeersByName(beerName, pageRequest);
        } else if (!StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = listBeersByBeerStyle(beerStyle, pageRequest);
        }  else if (StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = listBeersByNameAndBeerStyle(beerName, beerStyle, pageRequest);
        }  else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if(showInventory != null && !showInventory) {
            beerPage.forEach(beer -> beer.setQuantityOnHand(null));
        }

        return  beerPage.map(beerMapper::beerToBeerDto);

//        return beerPage.stream().map(beerMapper::beerToBeerDto).collect(Collectors.toList());
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0 ) {
            queryPageNumber = pageNumber -1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 1000) {
                queryPageSize = 1000;
            } else {
                queryPageSize = pageSize;
            }
        }

        Sort sort = Sort.by(Sort.Order.asc("beerName"));

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }

    private Page<Beer> listBeersByNameAndBeerStyle(String beerName, BeerStyle beerStyle, PageRequest pageRequest) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%" + beerName + "%", beerStyle, pageRequest);
    }

    public Page<Beer> listBeersByName(String beerName, PageRequest pageRequest) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%", pageRequest);
    }

    public Page<Beer> listBeersByBeerStyle(BeerStyle beerStyle, PageRequest pageRequest) {
        return beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
    }

    @Override
    public Optional<BeerDTO> getBeerByID(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id).orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDT0) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDT0)));
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();
        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            foundBeer.setBeerStyle(beer.getBeerStyle());
            foundBeer.setBeerName(beer.getBeerName());
            foundBeer.setPrice(beer.getPrice());
            foundBeer.setUpc(beer.getUpc());
            atomicReference.set(Optional.of(beerMapper
                    .beerToBeerDto(beerRepository.save(foundBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteBeer(UUID beerId) {
        if  (beerRepository.existsById(beerId)){
            beerRepository.deleteById(beerId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            if (StringUtils.hasText(beer.getBeerName())) {
                foundBeer.setBeerName(beer.getBeerName());
            }
            if (beer.getBeerStyle() != null) {
                foundBeer.setBeerStyle(beer.getBeerStyle());
            }
            if (beer.getQuantityOnHand() != null) {
                foundBeer.setQuantityOnHand(beer.getQuantityOnHand());
            }
            if (beer.getPrice() != null) {
                foundBeer.setPrice(beer.getPrice());
            }
            if(StringUtils.hasText(beer.getUpc())) {
                foundBeer.setUpc(beer.getUpc());
            }
            atomicReference.set(Optional.of(beerMapper
                    .beerToBeerDto(beerRepository.save(foundBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();

    }
}
