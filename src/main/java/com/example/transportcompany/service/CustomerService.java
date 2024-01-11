package com.example.transportcompany.service;

import com.example.transportcompany.dto.CustomerDTO;
import com.example.transportcompany.model.Customer;
import com.example.transportcompany.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepo;
    @Autowired
    public CustomerService(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    /**
     * Saves a customer record to the database based on the provided CustomerDTO.
     *
     * @param customerDTO  The data transfer object containing customer information.
     */
    public void saveCustomer(CustomerDTO customerDTO) {
        Customer customerToSave = new Customer();
        customerToSave.setName(customerDTO.getName());
        customerRepo.save(customerToSave);
    }
    /**
     * Updates an existing customer's name by ID using the provided CustomerDTO.
     *
     * @param customerId      The ID of the customer to update.
     * @param updatedCustomer The data transfer object containing updated customer information.
     * @throws EntityNotFoundException If a customer with such ID not found.
     */
    public void updateCustomerById(long customerId, CustomerDTO updatedCustomer) {
        Customer customerToUpdate = findCustomerById(customerId);
        String newName = updatedCustomer.getName();
        customerToUpdate.setName(newName);
        customerRepo.save(customerToUpdate);
    }

    /**
     * Deletes a customer by ID.
     *
     * @param id The ID of the customer to be deleted.
     * @throws EntityNotFoundException If the customer with the specified ID is not found.
     */
    public void deleteCustomerById(long id) {
            findCustomerById(id);
            customerRepo.deleteById(id);
    }

    /**
     * Finds a customer by their ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return The Customer object corresponding to the given ID.
     * @throws EntityNotFoundException If the customer with the specified ID is not found.
     */
    public Customer findCustomerById(long id) {
        return customerRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }

    /**
     * Retrieves all customers.
     *
     * @return A list containing all customers.
     */
    public List<Customer> findAllCustomer() {
        return customerRepo.findAll();
    }

    /**
     * Adds customers to a set based on a set of customer IDs.
     *
     * @param customerIds A set of customer IDs.
     * @return A set containing the corresponding customers based on the provided IDs.
     * @throws EntityNotFoundException If the customer with the specified ID is not found.
     */
    public Set<Customer> addCustomersToSet(Set<Long> customerIds){
        return customerIds.stream()
                .map(customerId -> findCustomerById(customerId))
                .collect(Collectors.toSet());
    }

}
