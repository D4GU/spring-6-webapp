package com.springframework.spring6restmvc.services;

import com.springframework.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDTO> getCustomerList();

    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO saveNewCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateCustomer(UUID customerId, CustomerDTO customer);

    Boolean deleteCustomer(UUID customerId);

    Optional<CustomerDTO> patchCustomer(UUID customerId, CustomerDTO customer);
}
