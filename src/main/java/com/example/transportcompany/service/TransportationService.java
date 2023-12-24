package com.example.transportcompany.service;

import com.example.transportcompany.dto.TransportationDTO;
import com.example.transportcompany.model.*;
import com.example.transportcompany.repository.CustomerRepository;
import com.example.transportcompany.repository.EmployeeRepository;
import com.example.transportcompany.repository.LoadRepository;
import com.example.transportcompany.repository.TransportationRepository;
import com.example.transportcompany.util.TransportationUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TransportationService {
    private final TransportationRepository transportationRepo;
    private final LoadRepository loadRepo;
    private final EmployeeRepository employeeRepo;
    private final CustomerRepository customerRepo;
    private final TransportCompanyService companyService;
    @Autowired
    public TransportationService(TransportationRepository transportationRepo,
                                 LoadRepository loadRepo,
                                 EmployeeRepository employeeRepo,
                                 CustomerRepository customerRepo,
                                 TransportCompanyService companyService) {
        this.transportationRepo = transportationRepo;
        this.loadRepo = loadRepo;
        this.employeeRepo = employeeRepo;
        this.customerRepo = customerRepo;
        this.companyService = companyService;
    }


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
    public void addLoads(long transportationId, Set<Long> loadIds){
        Transportation transportation = transportationRepo.findById(transportationId)
                .orElseThrow(() -> new EntityNotFoundException("Transportation not found with id: " + transportationId));
        Set<Load>loadsToSave = addLoadsToSet(loadIds);
        transportation.setLoads(loadsToSave);
        transportationRepo.save(transportation);
    }

    public void editLoads(long transportationId, Long loadId) {
        Transportation transportation = transportationRepo.findById(transportationId)
                .orElseThrow(() -> new EntityNotFoundException("Transportation not found with id: " + transportationId));
        Load load = loadRepo.findById(loadId)
                .orElseThrow(() -> new EntityNotFoundException("Load not found with id: " + loadId));
        Set<Load>updatedLoads = transportation.getLoads();
        updatedLoads.add(load);
        transportation.setLoads(updatedLoads);
        transportationRepo.save(transportation);
    }
    public void deleteLoads(long transportationId, Set<Long> loadIds){
        Transportation transportation = transportationRepo.findById(transportationId)
                .orElseThrow(() -> new EntityNotFoundException("Transportation not found with id: " + transportationId));
        Set<Load>currentLoads = transportation.getLoads();
        for (Long loadId:loadIds) {
            Load load = loadRepo.findById(loadId)
                    .orElseThrow(() -> new EntityNotFoundException("Load not found with id: " + loadId));
            if (!currentLoads.remove(load)){
                throw new RuntimeException("The Transportation didn't include a load with id " + loadId);
            }
        }
    }

    public void addCustomer(long transportationId, Set<Long> customerIds){
        Transportation transportation = transportationRepo.findById(transportationId)
                .orElseThrow(() -> new EntityNotFoundException("Transportation not found with id: " + transportationId));
        Set<Customer>customersToSave = addCustomersToSet(customerIds);
        transportation.setCustomers(customersToSave);
        transportationRepo.save(transportation);
    }
    public void editCustomers(long transportationId, Long customerId) {
        Transportation transportation = transportationRepo.findById(transportationId)
                .orElseThrow(() -> new EntityNotFoundException("Transportation not found with id: " + transportationId));
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Load not found with id: " + customerId));
        Set<Customer>updatedCustomers = transportation.getCustomers();
        updatedCustomers.add(customer);
        transportation.setCustomers(updatedCustomers);
        transportationRepo.save(transportation);
    }
    public void deleteCustomers(long transportationId, Set<Long> customerIds){
        Transportation transportation = transportationRepo.findById(transportationId)
                .orElseThrow(() -> new EntityNotFoundException("Transportation not found with id: " + transportationId));
        Set<Customer>currentCustomers = transportation.getCustomers();
        for (Long customerId:customerIds) {
            Customer customer = customerRepo.findById(customerId)
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerId));
            if (!currentCustomers.remove(customer)){
                throw new RuntimeException("The Transportation didn't include a load with id " + customerId);
            }
        }
    }


    public Set<Load> addLoadsToSet( Set<Long>loadIds ){
        Set<Load>loads = new HashSet<>();
        for (Long loadId:loadIds) {
            Load load = loadRepo.findById(loadId)
                    .orElseThrow(() -> new EntityNotFoundException("Load not found with id: " + loadId));
            loads.add(load);
        }
        return loads;
    }

    public Set<Customer> addCustomersToSet(Set<Long> customerIds){
        Set<Customer>customers = new HashSet<>();
        for (Long customerId:customerIds) {
            Customer customer = customerRepo.findById(customerId)
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerId));
            customers.add(customer);
        }
        return customers;
    }


    public List<Transportation> sortByDestination() {
        return transportationRepo.sortByEndPoint();
    }

    public List<Transportation> filterByDestination(String endPoint) {
        return transportationRepo.findByDestination(endPoint);
    }


    public void writeToFile(String fileName) {
        String filePath = "FILES/" + fileName;
        List<Transportation>transportations = findAllTransportation();
        TransportationUtil.writeTransportations(filePath, transportations);
    }

    public String readFromFile(String fileName) {
        //exception handling if file doesnt exist
        String filePath = "FILES/" + fileName;
        return TransportationUtil.readTransportations(filePath);
    }

    public long getCountByCompany(long companyId) {
        return transportationRepo.getCountByCompanyId(companyId);
    }


    public BigDecimal sumTransportationRevenue(long transportationId) {
        Transportation transportation = findTransportationById(transportationId);
        TransportationRate companyRates =  transportation.getCompany().getTransportationRate();
        BigDecimal numberCustomers = BigDecimal.valueOf( getNumberOfCustomers(transportation) );
        BigDecimal totalLoadWeight = BigDecimal.valueOf(sumTotalLoadWeight(transportation));
        BigDecimal loadRate = companyRates.getLoadRate();
        BigDecimal customerRate = companyRates.getCustomerRate();
        return customerRate.multiply(numberCustomers).add(loadRate.multiply(totalLoadWeight));
    }

    public double sumTotalLoadWeight(Transportation transportation){
        Set<Load>loads = transportation.getLoads();
        double totalWeight = 0.0;
        for (Load load: loads) {
            totalWeight+=load.getWeight();
        }
        return totalWeight;
    }

    public int getNumberOfCustomers(Transportation transportation){
        Set<Customer>customers = transportation.getCustomers();
        return customers.size();
    }


    public BigDecimal getRevenueByCompany(long companyId) {
        List<Transportation> transportations = transportationRepo.findByCompanyId(companyId);
        BigDecimal companyRevenue= BigDecimal.valueOf(0);
        for (Transportation transportation: transportations) {
            long id = transportation.getId();
            BigDecimal transportationRevenue = sumTransportationRevenue(id);
            companyRevenue = companyRevenue.add(transportationRevenue);
        }
        return companyRevenue;
    }


    public HashMap<Employee, BigDecimal> getRevenuePerEmployee(long companyId) {
        List<Employee> employees = employeeRepo.findByCompanyId(companyId);
        HashMap<Employee, BigDecimal> revenuePerEmployee = new HashMap<>();
        for (Employee employee: employees) {
            long id = employee.getId();
            BigDecimal employeeRevenue = getRevenueOfEmployee(id);
            revenuePerEmployee.put(employee, employeeRevenue);
        }
        return revenuePerEmployee;
    }

    public BigDecimal getRevenueOfEmployee(long employeeId) {
        List<Transportation> transportations = transportationRepo.findByEmployeeId(employeeId);
        BigDecimal employeeRevenue = BigDecimal.valueOf(0);
        for (Transportation transportation: transportations) {
            long id = transportation.getId();
            BigDecimal transportationRevenue = sumTransportationRevenue(id);
            employeeRevenue = employeeRevenue.add(transportationRevenue);
        }
        return employeeRevenue;
    }


    public BigDecimal getRevenueByTimePeriod(long companyId, LocalDate fromDate, LocalDate toDate ) {
        List<Transportation> transportations = transportationRepo.findByPeriodAndCompany(companyId, fromDate, toDate);
        BigDecimal revenue = BigDecimal.valueOf(0);
        for (Transportation transportation: transportations) {
            long id = transportation.getId();
            BigDecimal transportationRevenue = sumTransportationRevenue(id);
            revenue = revenue.add(transportationRevenue);
        }
        return revenue;
    }

    // TODO make this method:
    //public BigDecimal sumTotalRevenueByCompany(long companyId, LocalDate fromDate, LocalDate toDate ) {



    }
