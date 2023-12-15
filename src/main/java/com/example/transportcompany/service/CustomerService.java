package com.example.transportcompany.service;

import com.example.transportcompany.dto.CustomerDTO;
import com.example.transportcompany.model.Customer;
import com.example.transportcompany.model.TransportCompany;
import com.example.transportcompany.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepo;
    @Autowired
    public CustomerService(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    public void saveCustomer(CustomerDTO customerDTO) {
        Customer customerToSave = new Customer();
        customerToSave.setCustomerName(customerDTO.getName());
        customerRepo.save(customerToSave);
    }
    public void updateCustomerById(long customerId, CustomerDTO updatedCustomer) {
        Customer customerToUpdate = customerRepo.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerId));

        String newName = updatedCustomer.getName();
        customerToUpdate.setCustomerName(newName);
        customerRepo.save(customerToUpdate);
    }
    public void deleteCustomerById(long id) {
        customerRepo.deleteById(id);
    }
    public Customer findCustomerById(long id) {
        return customerRepo.findById(id).orElse(null);
    }

    public List<Customer> findAllCustomer() {
        return customerRepo.findAll();
    }


}
