package com.example.transportcompany.controller;

import com.example.transportcompany.dto.CustomerDTO;
import com.example.transportcompany.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> postCustomer(@RequestBody CustomerDTO customerDTO){
        customerService.saveCustomer(customerDTO);
        return ResponseEntity.ok("New Customer added");
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<String> patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
        try {
            customerService.updateCustomerById(id, customerDTO);
            return ResponseEntity.ok("The Customer has been edited");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id){
        try {
            customerService.deleteCustomerById(id);
            return ResponseEntity.ok("The Customer has been deleted");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

