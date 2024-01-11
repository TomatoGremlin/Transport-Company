package com.example.transportcompany.service;

import com.example.transportcompany.dto.TransportationDTO;
import com.example.transportcompany.model.*;
import com.example.transportcompany.repository.*;
import jakarta.persistence.EntityNotFoundException;
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

    /**
     * Saves a new transportation based on the provided TransportationDTO.
     *
     * @param transportationDTO The data transfer object containing transportation information.
     * @throws IllegalArgumentException If the arrival date is not after the departure date.
     */
    public void saveTransportation(TransportationDTO transportationDTO) {
        checkDates(transportationDTO.getArrivalDate(), transportationDTO.getDepartureDate());

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
    /**
     * Updates an existing transportation by ID using the provided TransportationDTO.
     *
     * @param transportationId        The ID of the transportation to update.
     * @param updatedTransportation   The data transfer object containing updated transportation information.
     * @throws IllegalArgumentException If the arrival date is not after the departure date.
     * @throws EntityNotFoundException If the transportation with the specified ID is not found.
     */
    public void updateTransportationById(long transportationId, TransportationDTO updatedTransportation) {
        checkDates(updatedTransportation.getArrivalDate(), updatedTransportation.getDepartureDate());

        Transportation transportationToUpdate = findTransportationById(transportationId);
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

    // Helper method to check if arrival date is before departure date
    /**
     * Checks if the arrival date is before the departure date.
     *
     * @param arrivalDate   The date of arrival.
     * @param departureDate The date of departure.
     * @throws IllegalArgumentException If the arrival date is not after the departure date.
     */
    private void checkDates(LocalDate arrivalDate, LocalDate departureDate) {
        if (arrivalDate.isBefore(departureDate)) {
            throw new IllegalArgumentException("Arrival date must be after departure date.");
        }
    }

    /**
     * Deletes a transportation by ID.
     *
     * @param id The ID of the transportation to be deleted.
     * @throws EntityNotFoundException If the transportation with the specified ID is not found.
     */
    public void deleteTransportationById(long id) {
            findTransportationById(id);
            transportationRepo.deleteById(id);
    }
    /**
     * Finds a transportation by its ID.
     *
     * @param id The ID of the transportation to retrieve.
     * @return The Transportation object corresponding to the given ID.
     * @throws EntityNotFoundException If the transportation with the specified ID is not found.
     */
    public Transportation findTransportationById(long id) {
        return transportationRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Transportation not found with id: " + id));
    }
    /**
     * Retrieves a list of all transportations.
     *
     * @return A list containing all transportations.
     */
    public List<Transportation> findAllTransportations() {
        return transportationRepo.findAll();
    }

    /**
     * Retrieves transportations associated with a specific company.
     *
     * @param companyId The ID of the company to retrieve transportations for.
     * @return A list of transportations associated with the specified company.
     */
    public List<Transportation> findByCompany(long companyId){
        return transportationRepo.findByCompanyId(companyId);
    }

    /**
     * Retrieves transportations associated with a specific employee.
     *
     * @param employeeId The ID of the employee to retrieve transportations for.
     * @return A list of transportations associated with the specified employee.
     */
    public List<Transportation> findByEmployee(long employeeId){
        return transportationRepo.findByEmployeeId(employeeId);
    }

    /**
     * Retrieves transportations within a specific period associated with a company.
     *
     * @param companyId The ID of the company to retrieve transportations for.
     * @param fromDate  The start date of the period.
     * @param toDate    The end date of the period.
     * @return A list of transportations associated with the specified company within the given period.
     */
    public List<Transportation> findByPeriodAndCompany(long companyId, LocalDate fromDate, LocalDate toDate){
        return transportationRepo.findByPeriodAndCompany(companyId, fromDate, toDate);
    }
    /**
     * Assigns an employee to a transportation.
     *
     * @param transportationId The ID of the transportation to assign the employee to.
     * @param employeeId       The ID of the employee to be assigned.
     */
    public void assignEmployee(long transportationId, long employeeId){
        Transportation transportation = findTransportationById(transportationId);
        Employee employee = employeeService.findEmployeeById(employeeId);
        transportation.setEmployee(employee);
        transportationRepo.save(transportation);
    }
    /**
     * Edits loads associated with a transportation.
     *
     * @param transportationId The ID of the transportation to edit loads for.
     * @param loadIds          The set of load IDs to be associated with the transportation.
     */
    public void editLoads(long transportationId, Set<Long> loadIds){
        Transportation transportation = findTransportationById(transportationId);
        Set<Load>loadsToSave = loadService.addLoadsToSet(loadIds);
        transportation.setLoads(loadsToSave);
        transportationRepo.save(transportation);
    }
    /**
     * Adds a load to a transportation.
     *
     * @param transportationId The ID of the transportation to add the load to.
     * @param loadId           The ID of the load to be added.
     */
    public void addLoad(long transportationId, Long loadId) {
        Transportation transportation = findTransportationById(transportationId);
        Load load = loadService.findLoadById(loadId);
        Set<Load>updatedLoads = transportation.getLoads();
        updatedLoads.add(load);
        transportation.setLoads(updatedLoads);
        transportationRepo.save(transportation);
    }
    /**
     * Deletes loads from a transportation.
     *
     * @param transportationId The ID of the transportation to delete loads from.
     * @param loadIds          The set of load IDs to be deleted.
     * @throws RuntimeException If one or more specified loads are not part of the transportation.
     */
    public void deleteLoads(long transportationId, Set<Long> loadIds){
        Transportation transportation = findTransportationById(transportationId);
        Set<Load> loadsToDelete = loadService.addLoadsToSet(loadIds);
        Set<Load> currentLoads = transportation.getLoads();
        if (!currentLoads.removeAll(loadsToDelete)) {
            throw new RuntimeException("The Transportation didn't include one or more specified loads");
        }
    }

    /**
     * Edits customers associated with a transportation.
     *
     * @param transportationId The ID of the transportation to edit customers for.
     * @param customerIds      The set of customer IDs to be associated with the transportation.
     */
    public void editCustomers(long transportationId, Set<Long> customerIds){
        Transportation transportation = findTransportationById(transportationId);
        Set<Customer>customersToSave = customerService.addCustomersToSet(customerIds);
        transportation.setCustomers(customersToSave);
        transportationRepo.save(transportation);
    }

    /**
     * Adds a customer to a transportation.
     *
     * @param transportationId The ID of the transportation to add the customer to.
     * @param customerId       The ID of the customer to be added.
     */
    public void addCustomer(long transportationId, Long customerId) {
        Transportation transportation = findTransportationById(transportationId);
        Customer customer = customerService.findCustomerById(customerId);
        Set<Customer>updatedCustomers = transportation.getCustomers();
        updatedCustomers.add(customer);
        transportation.setCustomers(updatedCustomers);
        transportationRepo.save(transportation);
    }
    /**
     * Deletes customers from a transportation.
     *
     * @param transportationId The ID of the transportation to delete customers from.
     * @param customerIds      The set of customer IDs to be deleted.
     * @throws RuntimeException If one or more specified customers are not part of the transportation.
     */
    public void deleteCustomers(long transportationId, Set<Long> customerIds){
        Transportation transportation = findTransportationById(transportationId);
        Set<Customer> customersToDelete = customerService.addCustomersToSet(customerIds);
        Set<Customer> currentCustomers = transportation.getCustomers();
        if (!currentCustomers.removeAll(customersToDelete)) {
            throw new RuntimeException("The Transportation didn't include one or more specified customers");
        }
    }

    /**
     * Sorts the list of transportations by start point.
     *
     * @return A list of transportations sorted by start point.
     */
    public List<Transportation> sortByStartPoint() {
        return transportationRepo.sortByStartPoint();
    }

    /**
     * Sorts the list of transportations by end point.
     *
     * @return A list of transportations sorted by end point.
     */
    public List<Transportation> sortByEndPoint() {
        return transportationRepo.sortByEndPoint();
    }

    /**
     * Retrieves transportations filtered by destination (end point).
     *
     * @param endPoint The destination point to filter transportations by.
     * @return A list of transportations with the specified destination.
     */
    public List<Transportation> filterByDestination(String endPoint) {
        return transportationRepo.findByDestination(endPoint);
    }

    /**
     * Retrieves the number of transportations associated with a company.
     *
     * @param companyId The ID of the company to count transportations for.
     * @return The count of transportations associated with the specified company.
     */
    public long getCountByCompany(long companyId) {
        return transportationRepo.getCountByCompanyId(companyId);
    }

}
