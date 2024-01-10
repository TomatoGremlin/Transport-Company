package com.example.transportcompany.service;

import com.example.transportcompany.dto.TransportationDTO;
import com.example.transportcompany.model.*;
import com.example.transportcompany.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransportationService {
    private final TransportationRepository transportationRepo;
    private final LoadService loadService;
    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final TransportCompanyService transportCompanyService;
    @Autowired
    public TransportationService(TransportationRepository transportationRepo,
                                 LoadService loadService,
                                 EmployeeService employeeService,
                                 CustomerService customerService,
                                 TransportCompanyService transportCompanyService) {
        this.transportationRepo = transportationRepo;
        this.loadService = loadService;
        this.employeeService = employeeService;
        this.customerService = customerService;
        this.transportCompanyService = transportCompanyService;
    }


    public void saveTransportation(TransportationDTO transportationDTO) {
        Transportation transportationToSave = new Transportation();
        transportationToSave.setStartPoint(transportationDTO.getStartPoint());
        transportationToSave.setEndPoint(transportationDTO.getEndPoint());
        transportationToSave.setDepartureDate(transportationDTO.getDepartureDate());
        transportationToSave.setArrivalDate(transportationDTO.getArrivalDate());
        transportationToSave.setPaymentStatus(transportationDTO.isPaymentStatus());

        long companyId = transportationDTO.getCompanyId();
        TransportCompany transportCompany = transportCompanyService.findCompanyById(companyId);
        transportationToSave.setCompany(transportCompany);
        transportationRepo.save(transportationToSave);
    }
    public void updateTransportationById(long transportationId, TransportationDTO updatedTransportation) {
        Transportation transportationToUpdate = findTransportationById(transportationId);
               // .orElseThrow(() -> new EntityNotFoundException("Transportation not found with id: " + transportationId));

        String newStartPoint = updatedTransportation.getStartPoint();
        String newEndPoint = updatedTransportation.getEndPoint();
        LocalDate newDepartureDate = updatedTransportation.getDepartureDate();
        LocalDate newArrivalDate =  updatedTransportation.getArrivalDate();
        boolean newPaymentStatus = updatedTransportation.isPaymentStatus();

        TransportCompany newCompany = transportCompanyService.findCompanyById(updatedTransportation.getCompanyId());

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
    public List<Transportation> findAllTransportations() {
        return transportationRepo.findAll();
    }

    public List<Transportation> findByCompany(long companyId){
        return transportationRepo.findByCompanyId(companyId);
    }

    public List<Transportation> findByEmployee(long employeeId){
        return transportationRepo.findByEmployeeId(employeeId);
    }

    public List<Transportation> findByPeriodAndCompany(long companyId, LocalDate fromDate, LocalDate toDate){
        return transportationRepo.findByPeriodAndCompany(companyId, fromDate, toDate);
    }

    public void assignEmployee(long transportationId, long employeeId){
        Transportation transportation = findTransportationById(transportationId);
        Employee employee = employeeService.findEmployeeById(employeeId);
        transportation.setEmployee(employee);
        transportationRepo.save(transportation);
    }
    public void editLoads(long transportationId, Set<Long> loadIds){
        Transportation transportation = findTransportationById(transportationId);
        Set<Load>loadsToSave = loadService.addLoadsToSet(loadIds);
        transportation.setLoads(loadsToSave);
        transportationRepo.save(transportation);
    }

    public void addLoad(long transportationId, Long loadId) {
        Transportation transportation = findTransportationById(transportationId);
        Load load = loadService.findLoadById(loadId);
        Set<Load>updatedLoads = transportation.getLoads();
        updatedLoads.add(load);
        transportation.setLoads(updatedLoads);
        transportationRepo.save(transportation);
    }
    public void deleteLoads(long transportationId, Set<Long> loadIds){
        Transportation transportation = findTransportationById(transportationId);
        Set<Load> loadsToDelete = loadService.addLoadsToSet(loadIds);
        Set<Load> currentLoads = transportation.getLoads();
        if (!currentLoads.removeAll(loadsToDelete)) {
            throw new RuntimeException("The Transportation didn't include one or more specified loads");
        }
    }

    public void editCustomers(long transportationId, Set<Long> customerIds){
        Transportation transportation = findTransportationById(transportationId);
        Set<Customer>customersToSave = customerService.addCustomersToSet(customerIds);
        transportation.setCustomers(customersToSave);
        transportationRepo.save(transportation);
    }
    public void addCustomer(long transportationId, Long customerId) {
        Transportation transportation = findTransportationById(transportationId);
        Customer customer = customerService.findCustomerById(customerId);
        Set<Customer>updatedCustomers = transportation.getCustomers();
        updatedCustomers.add(customer);
        transportation.setCustomers(updatedCustomers);
        transportationRepo.save(transportation);
    }
    public void deleteCustomers(long transportationId, Set<Long> customerIds){
        Transportation transportation = findTransportationById(transportationId);
        Set<Customer> customersToDelete = customerService.addCustomersToSet(customerIds);
        Set<Customer> currentCustomers = transportation.getCustomers();
        if (!currentCustomers.removeAll(customersToDelete)) {
            throw new RuntimeException("The Transportation didn't include one or more specified customers");
        }
    }

    public List<Transportation> sortByStartPoint() {
        return transportationRepo.sortByStartPoint();
    }
    public List<Transportation> sortByEndPoint() {
        return transportationRepo.sortByEndPoint();
    }

    public List<Transportation> filterByDestination(String endPoint) {
        return transportationRepo.findByDestination(endPoint);
    }

    public long getCountByCompany(long companyId) {
        return transportationRepo.getCountByCompanyId(companyId);
    }

   /* public BigDecimal sumTransportationRevenue(long transportationId) {
        Transportation transportation = findTransportationById(transportationId);
        TransportationRate companyRates =  transportation.getCompany().getTransportationRate();
        BigDecimal numberCustomers = BigDecimal.valueOf( getNumberOfCustomers(transportation) );
        BigDecimal totalLoadWeight = BigDecimal.valueOf(sumTotalLoadWeight(transportation));
        BigDecimal loadRate = companyRates.getLoadRate();
        BigDecimal customerRate = companyRates.getCustomerRate();
        return customerRate.multiply(numberCustomers).add(loadRate.multiply(totalLoadWeight));
    }

    public double sumTotalLoadWeight(Transportation transportation){
        Set<Load> loads = transportation.getLoads();
        return loads.stream()
                .mapToDouble(Load::getWeight)
                .sum();
    }

    public int getNumberOfCustomers(Transportation transportation){
        Set<Customer>customers = transportation.getCustomers();
        return customers.size();
    }


    public BigDecimal getRevenueByCompany(long companyId) {
        List<Transportation> transportations = transportationRepo.findByCompanyId(companyId);
        return transportations.stream()
                .map(Transportation::getId)
                .map(this::sumTransportationRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public BigDecimal getRevenueOfEmployee(long employeeId) {
        List<Transportation> transportations = transportationRepo.findByEmployeeId(employeeId);
        return transportations.stream()
                .map(Transportation::getId)
                .map(this::sumTransportationRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public HashMap<Employee, BigDecimal> getRevenueOfEachEmployee(long companyId) {
        List<Employee> employees = employeeService.findByCompany(companyId);
        return employees.stream()
                .collect(Collectors.toMap(
                        employee -> employee,
                        employee -> getRevenueOfEmployee(employee.getId()),
                        (oldValue, newValue) -> newValue,
                        HashMap::new
                ));
    }

    public BigDecimal getRevenueByTimePeriod(long companyId, LocalDate fromDate, LocalDate toDate ) {
        List<Transportation> transportations = transportationRepo.findByPeriodAndCompany(companyId, fromDate, toDate);
        return transportations.stream()
                .map(Transportation::getId)
                .map(this::sumTransportationRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }*/


}
