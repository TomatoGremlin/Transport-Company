package com.example.transportcompany.service;

import com.example.transportcompany.dto.TransportationDTO;
import com.example.transportcompany.model.*;
import com.example.transportcompany.repository.CustomerRepository;
import com.example.transportcompany.repository.EmployeeRepository;
import com.example.transportcompany.repository.LoadRepository;
import com.example.transportcompany.repository.TransportationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class TransportationService {
    @Autowired
    private TransportationRepository transportationRepo;
    @Autowired
    private LoadRepository loadRepo;
    @Autowired
    private EmployeeRepository employeeRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private TransportCompanyService companyService;

    public void saveTransportation(TransportationDTO transportationDTO) {
        Transportation transportationToSave = new Transportation();
        transportationToSave.setStartPoint(transportationDTO.getStartPoint());
        transportationToSave.setEndPoint(transportationDTO.getEndPoint());
        transportationToSave.setDepartureDate(transportationDTO.getDepartureDate());
        transportationToSave.setArrivalDate(transportationDTO.getArrivalDate());
        transportationToSave.setPaymentStatus(transportationDTO.isPaymentStatus());

        long companyId = transportationDTO.getCompanyId();
        TransportCompany transportCompany = companyService.findCompanyById(companyId);
        transportationToSave.setCompany(transportCompany);
        transportationRepo.save(transportationToSave);
    }
    public void updateTransportationById(long transportationId, TransportationDTO updatedTransportation) {
        Transportation transportationToUpdate = transportationRepo.findById(transportationId)
                .orElseThrow(() -> new EntityNotFoundException("Transportation not found with id: " + transportationId));

        String newStartPoint = updatedTransportation.getStartPoint();
        String newEndPoint = updatedTransportation.getEndPoint();
        LocalDate newDepartureDate = updatedTransportation.getDepartureDate();
        LocalDate newArrivalDate =  updatedTransportation.getArrivalDate();
        boolean newPaymentStatus = updatedTransportation.isPaymentStatus();

        TransportCompany newCompany = companyService.findCompanyById(updatedTransportation.getCompanyId());
        transportationToUpdate.setDepartureDate(newDepartureDate);
        transportationToUpdate.setArrivalDate(newArrivalDate);
        transportationToUpdate.setStartPoint(newStartPoint);
        transportationToUpdate.setEndPoint(newEndPoint);
        transportationToUpdate.setPaymentStatus(newPaymentStatus);
        transportationToUpdate.setCompany(newCompany);
        transportationRepo.save(transportationToUpdate);
    }
    public void deleteTransportationById(long id) {
        transportationRepo.deleteById(id);
    }
    public Transportation findTransportationById(long id) {
        return transportationRepo.findById(id).orElse(null);
    }

    public List<Transportation> findAllTransportation() {
        return transportationRepo.findAll();
    }

    public void assignEmployee(long transportationId, long employeeId){
        Transportation transportation = transportationRepo.findById(transportationId)
                .orElseThrow(() -> new EntityNotFoundException("Transportation not found with id: " + transportationId));
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));
        transportation.setEmployee(employee);
        transportationRepo.save(transportation);
    }
    public void addLoad(long transportationId, long loadId){
            Transportation transportation = transportationRepo.findById(transportationId)
                    .orElseThrow(() -> new EntityNotFoundException("Transportation not found with id: " + transportationId));
            Load load = loadRepo.findById(loadId)
                    .orElseThrow(() -> new EntityNotFoundException("Load not found with id: " + loadId));
            Set<Load>updatedLoads = addLoadToSet(load, transportation);
            transportation.setLoadList(updatedLoads);
            transportationRepo.save(transportation);
    }

    public void addCustomer(long transportationId, long customerId){
        Transportation transportation = transportationRepo.findById(transportationId)
                .orElseThrow(() -> new EntityNotFoundException("Transportation not found with id: " + transportationId));
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerId));
        Set<Customer>updatedCustomers = addCustomerToSet(customer, transportation);
        transportation.setCustomerList(updatedCustomers);
        transportationRepo.save(transportation);
    }


    public Set<Load> addLoadToSet(Load load, Transportation transportation){
        Set<Load> currentLoads = transportation.getLoadList();
        currentLoads.add(load);
        return currentLoads;
    }

    public Set<Customer> addCustomerToSet(Customer customer, Transportation transportation){
        Set<Customer> currentCustomers = transportation.getCustomerList();
        currentCustomers.add(customer);
        return currentCustomers;
    }

}
