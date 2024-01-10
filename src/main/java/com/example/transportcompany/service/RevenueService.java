package com.example.transportcompany.service;

import com.example.transportcompany.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RevenueService {
    private final TransportationService transportationService;
    private final EmployeeService employeeService;
    private final TransportCompanyService transportCompanyService;
    @Autowired
    public RevenueService(TransportationService transportationService,
                                 EmployeeService employeeService,
                                 TransportCompanyService transportCompanyService) {
        this.transportationService = transportationService;
        this.employeeService = employeeService;
        this.transportCompanyService = transportCompanyService;
    }


    public BigDecimal calculateRevenueOfTransportation(long transportationId) {
        Transportation transportation = transportationService.findTransportationById(transportationId);
        TransportationRate companyRates =  transportation.getCompany().getTransportationRate();
        BigDecimal numberCustomers = BigDecimal.valueOf( getNumberOfCustomers(transportation) );
        BigDecimal totalLoadWeight = BigDecimal.valueOf(calculateTotalLoadWeight(transportation));
        BigDecimal loadRate = companyRates.getLoadRate();
        BigDecimal customerRate = companyRates.getCustomerRate();
        return customerRate.multiply(numberCustomers).add(loadRate.multiply(totalLoadWeight));
    }

    public double calculateTotalLoadWeight(Transportation transportation){
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
        List<Transportation> transportations = transportationService.findByCompany(companyId);
        return transportations.stream()
                .map(Transportation::getId)
                .map(this::calculateRevenueOfTransportation)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getRevenueOfEmployee(long employeeId) {
        List<Transportation> transportations = transportationService.findByEmployee(employeeId);
        return transportations.stream()
                .map(Transportation::getId)
                .map(this::calculateRevenueOfTransportation)
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
        List<Transportation> transportations = transportationService.findByPeriodAndCompany(companyId, fromDate, toDate);
        return transportations.stream()
                .map(Transportation::getId)
                .map(this::calculateRevenueOfTransportation)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
