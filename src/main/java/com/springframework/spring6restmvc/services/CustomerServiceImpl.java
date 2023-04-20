package com.springframework.spring6restmvc.services;

import com.springframework.spring6restmvc.model.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl(){
        this.customerMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Daniel Guggisberg")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Joel Guggisberg")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
    }

    @Override
    public List<CustomerDTO> getCustomerList(){
        return new ArrayList<>(customerMap.values());
    }
    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.of(customerMap.get(id));
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(customer.getVersion())
                .customerName(customer.getCustomerName())
                .lastModifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();

        customerMap.put(savedCustomer.getId(), savedCustomer);

        return savedCustomer;
    }

    @Override
    public Optional<CustomerDTO> updateCustomer(UUID customerId, CustomerDTO customer) {
        CustomerDTO tempCustomer = customerMap.get(customerId);
        tempCustomer.setCustomerName(customer.getCustomerName());
        tempCustomer.setLastModifiedDate(LocalDateTime.now());
        customerMap.put(tempCustomer.getId(), tempCustomer);
        return null;
    }

    @Override
    public Boolean deleteCustomer(UUID customerId) {
        customerMap.remove(customerId);
        return true;
    }

    @Override
    public void patchCustomer(UUID customerId, CustomerDTO customer) {
        CustomerDTO tempCustomer = customerMap.get(customerId);

        if(StringUtils.hasText(customer.getCustomerName())) {
            tempCustomer.setCustomerName(customer.getCustomerName());
            tempCustomer.setLastModifiedDate(LocalDateTime.now());
        }
        customerMap.put(tempCustomer.getId(), tempCustomer);

    }
}
