package com.springframework.spring6restmvc.controller;

import com.springframework.spring6restmvc.entities.Beer;
import com.springframework.spring6restmvc.entities.Customer;
import com.springframework.spring6restmvc.mappers.CustomerMapper;
import com.springframework.spring6restmvc.model.BeerDTO;
import com.springframework.spring6restmvc.model.BeerStyle;
import com.springframework.spring6restmvc.model.CustomerDTO;
import com.springframework.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void testGetCustomerByIdFailed() {
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
    }

    @Test
    void testCustomerList() {
        List<CustomerDTO> customerDTOList = customerController.customerList();
        assertThat(customerDTOList.size()).isEqualTo(1);
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.updateCustomer(UUID.randomUUID(), CustomerDTO.builder()
                    .id(UUID.randomUUID()).build());
        });
    }

    @Test
    void deleteByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteById(UUID.randomUUID());
        });
        Customer customer = customerRepository.findAll().get(0);
        ResponseEntity responseEntity = customerController.deleteById(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customer.getId()).isEmpty());
    }

    @Rollback
    @Transactional
    @Test
    void deleteByIdFound() {
        Customer customer = customerRepository.findAll().get(0);
        ResponseEntity responseEntity = customerController.deleteById(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customer.getId()).isEmpty());
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateExistingCustomer() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);
        final String customerName = "UPDATED";
        customerDTO.setCustomerName(customerName);

        ResponseEntity responseEntity = customerController.updateCustomer(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getCustomerName()).isEqualTo(customerName);

    }

    @Rollback
    @Transactional
    @Test
    void testPatchExistingCustomer() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);

        final String customerName = "UPDATED";
        customerDTO.setCustomerName(customerName);

        ResponseEntity responseEntity = customerController.patchCustomer(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getCustomerName()).isEqualTo(customerName);

    }


    @Rollback
    @Transactional
    @Test
    void testSaveCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("Test")
                .build();
        ResponseEntity responseEntity = customerController.handlePost(customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] LocationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID uuid = UUID.fromString(LocationUUID[4]);

        Customer customer = customerRepository.findById(uuid).get();
        assertThat(customer).isNotNull();

    }

    @Test
    void getCustomerById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> customerDTOListDTOS = customerController.customerList();
        assertThat(customerDTOListDTOS.size()).isEqualTo(0);
    }
}