package com.springframework.spring6restmvc.controller;

import com.springframework.spring6restmvc.model.CustomerDTO;
import com.springframework.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RequestMapping
@RestController
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_ID = CUSTOMER_PATH + "/{customerId}";

    private CustomerService customerService;

    @PatchMapping(CUSTOMER_ID)
    public ResponseEntity patchCustomer(@PathVariable("customerId")UUID customerId, @RequestBody CustomerDTO customer) {
        customerService.patchCustomer(customerId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(CUSTOMER_ID)
    public ResponseEntity deleteById(@PathVariable("customerId") UUID customerId) {
        if (!customerService.deleteCustomer(customerId)) {
            throw new NotFoundException();
        }
        ;
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(CUSTOMER_ID)
    public ResponseEntity updateCustomer(@PathVariable("customerId")UUID customerId, @RequestBody CustomerDTO customer) {

        if(customerService.updateCustomer(customerId, customer).isEmpty()){
            throw new NotFoundException();
        }
        ;
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity handlePost(@RequestBody CustomerDTO customer){
        CustomerDTO savedCustomer = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(CUSTOMER_PATH)
    public List<CustomerDTO> customerList (){
        return customerService.getCustomerList();
    }

    @GetMapping(CUSTOMER_ID)
    public CustomerDTO getCustomerById(@PathVariable UUID customerId) {
        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }

}
