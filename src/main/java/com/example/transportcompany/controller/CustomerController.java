package com.example.transportcompany.controller;

import com.example.transportcompany.dto.CustomerDTO;
import com.example.transportcompany.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<String> postCustomer(@RequestBody CustomerDTO customerDTO){
        customerService.saveCustomer(customerDTO);
        return ResponseEntity.ok("New Customer added");
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<String> patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
        customerService.updateCustomerById(id, customerDTO);
        return ResponseEntity.ok("The Customer has been edited");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok("The Customer has been deleted");
    }

}

