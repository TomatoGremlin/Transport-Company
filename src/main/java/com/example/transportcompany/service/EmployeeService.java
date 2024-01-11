package com.example.transportcompany.service;

import com.example.transportcompany.dto.EmployeeDTO;
import com.example.transportcompany.model.*;
import com.example.transportcompany.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepo;
    private final TransportCompanyService transportCompanyService;
    private final VehicleService vehicleService;
    private final VehicleTypeService vehicleTypeService;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepo,
                           TransportCompanyService transportCompanyService,
                           VehicleService vehicleService,
                           VehicleTypeService vehicleTypeService) {
        this.employeeRepo = employeeRepo;
        this.transportCompanyService = transportCompanyService;
        this.vehicleService = vehicleService;
        this.vehicleTypeService = vehicleTypeService;
    }

    /**
     * Saves a new employee based on the provided EmployeeDTO.
     *
     * @param employeeDTO The data transfer object containing employee information.
     */
    public void saveEmployee(EmployeeDTO employeeDTO) {
        Employee employeeToSave = new Employee();
        employeeToSave.setName(employeeDTO.getName());
        employeeToSave.setSalary(employeeDTO.getSalary());
        long companyId = employeeDTO.getCompanyId();
        TransportCompany transportCompany = transportCompanyService.findCompanyById(companyId);
        employeeToSave.setCompany(transportCompany);
        employeeRepo.save(employeeToSave);
    }

    /**
     * Updates an existing employee's information by ID using the provided EmployeeDTO.
     *
     * @param employeeId      The ID of the employee to update.
     * @param updatedEmployee The data transfer object containing updated employee information.
     * @throws EntityNotFoundException If the employee with the specified ID is not found.
     */
    public void updateEmployeeById(long employeeId, EmployeeDTO updatedEmployee) {
        Employee employeeToUpdate = findEmployeeById(employeeId);

        String newName = updatedEmployee.getName();
        BigDecimal newSalary = updatedEmployee.getSalary();
        long newCompanyId = updatedEmployee.getCompanyId();
        TransportCompany newCompany = transportCompanyService.findCompanyById(newCompanyId);

        employeeToUpdate.setName(newName);
        employeeToUpdate.setSalary(newSalary);
        employeeToUpdate.setCompany(newCompany);
        employeeRepo.save(employeeToUpdate);
    }
    /**
     * Deletes an employee by ID.
     *
     * @param id The ID of the employee to be deleted.
     * @throws EntityNotFoundException If the employee with the specified ID is not found.
     */
    public void deleteEmployeeById(long id) {
            findEmployeeById(id);
            employeeRepo.deleteById(id);
    }

    /**
     * Adds a qualification (vehicle type) to an employee's set of qualifications.
     *
     * @param employeeId    The ID of the employee.
     * @param vehicleTypeId The ID of the vehicle type (qualification) to add.
     */
    public void addQualification(long employeeId, long vehicleTypeId) {
        Employee employee = findEmployeeById(employeeId);
        VehicleType vehicleType = vehicleTypeService.findVehicleTypeById(vehicleTypeId);
        Set<VehicleType> updatesQualifications = addVehicleTypeToSet(vehicleType, employee);
        employee.setQualifications(updatesQualifications);
        employeeRepo.save(employee);
    }
    /**
     * Assigns a vehicle to an employee if the employee holds the qualification to drive it.
     *
     * @param employeeId The ID of the employee.
     * @param vehicleId  The ID of the vehicle to assign.
     * @throws RuntimeException If the employee lacks the qualification to drive the vehicle.
     */
    public void assignVehicle(long employeeId, long vehicleId) {
        Employee employee = findEmployeeById(employeeId);
        Vehicle vehicle = vehicleService.findVehicleById(vehicleId);

        if(!doesVehicleQualificationMatch(employee, vehicle)){
            throw new RuntimeException("The Employee holds no qualification to drive the Vehicle.");
        }
        Set<Vehicle> updatedVehicles = addVehicleToSet(vehicle, employee);
        employee.setVehicles(updatedVehicles);
        employeeRepo.save(employee);
    }

    /**
     * Checks if an employee's qualifications match the vehicle's qualification.
     *
     * @param employee The employee to check qualifications.
     * @param vehicle  The vehicle to check qualifications against.
     * @return True if the employee's qualifications match the vehicle's qualification, false otherwise.
     */
    public boolean doesVehicleQualificationMatch(Employee employee, Vehicle vehicle){
       Set<VehicleType> driverQualifications = employee.getQualifications();
       VehicleType vehicleType = vehicle.getVehicleType();
       return driverQualifications.contains(vehicleType);
    }

    /**
     * Adds a vehicle to an employee's set of vehicles.
     *
     * @param vehicle  The vehicle to add.
     * @param employee The employee to whom the vehicle will be added.
     * @return The updated set of vehicles for the employee.
     */
    public Set<Vehicle> addVehicleToSet(Vehicle vehicle, Employee employee){
        Set<Vehicle> currentVehicles = employee.getVehicles();
        currentVehicles.add(vehicle);
        return currentVehicles;
    }
    /**
     * Adds a vehicle type to an employee's set of qualifications.
     *
     * @param vehicleType The vehicle type to add.
     * @param employee    The employee to whom the vehicle type will be added.
     * @return The updated set of vehicle types for the employee.
     */

    public Set<VehicleType> addVehicleTypeToSet(VehicleType vehicleType, Employee employee){
        Set<VehicleType> currentQualifications = employee.getQualifications();
        currentQualifications.add(vehicleType);
        return currentQualifications;
    }
    /**
     * Finds an employee by their ID.
     *
     * @param id The ID of the employee to retrieve.
     * @return The Employee object corresponding to the given ID.
     * @throws EntityNotFoundException If the employee with the specified ID is not found.
     */
    public Employee findEmployeeById(long id) {
        return employeeRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
    }

    /**
     * Retrieves a list of all employees.
     *
     * @return A list containing all employees.
     */
    public List<Employee> findAllEmployee() {
        return employeeRepo.findAll();
    }

    /**
     * Filters employees by company ID.
     *
     * @param companyId The ID of the company used for filtering employees.
     * @return A list of employees belonging to the specified company.
     */
    public List<Employee> filterByCompany(long companyId){
        return employeeRepo.findByCompanyId(companyId);
    }

    /**
     * Filters employees by salary greater than the specified value.
     *
     * @param salary The minimum salary value used as a filter criterion.
     * @return A list of employees with a salary greater than the specified value.
     */
    public List<Employee> filterBySalaryGreaterThan(BigDecimal salary) {
        return employeeRepo.filteredBySalaryGreaterThan(salary);
    }
    /**
     * Filters employees by a specific qualification (vehicle type) ID.
     *
     * @param qualificationId The ID of the qualification used for filtering employees.
     * @return A list of employees with the specified qualification.
     */
    public List<Employee> filterByQualification(long qualificationId) {
        return employeeRepo.filteredByQualification(qualificationId);
    }

    /**
     * Sorts employees by their salary in ascending order.
     *
     * @return A list of employees sorted by salary in ascending order.
     */
    public List<Employee> sortBySalary() {
        return employeeRepo.sortedBySalary();
    }


    /**
     * Sorts employees by their qualifications in ascending order.
     *
     * @return A list of employees sorted by qualifications in ascending order.
     */
    public HashMap<VehicleType,List<Employee>> sortByQualification() {
        List<VehicleType> orderedTypes = vehicleTypeService.sortByType();
        return orderedTypes.stream()
                .collect(Collectors.toMap(
                        type -> type,
                        type -> filterByQualification(type.getId()),
                        (existing, replacement) -> existing,
                        HashMap::new
                ));
    }

    /**
     * Counts the number of transportations assigned to each employee in a company.
     *
     * @param companyId The ID of the company.
     * @return A mapping of each employee to the count of transportations they're assigned.
     */

    public HashMap<Employee, Long> countTransportationsPerEmployee(long companyId) {
        TransportCompany company = transportCompanyService.findCompanyById(companyId);
        Set<Employee> employees = company.getEmployees();
        return employees.stream()
                .collect(Collectors.toMap(
                        employee -> employee,
                        employee -> countTransportationsOfEmployee(employee.getId()),
                        (existing, replacement) -> existing,
                        HashMap::new
                ));
    }
    /**
     * Counts the number of transportations assigned to a specific employee.
     *
     * @param employeeId The ID of the employee.
     * @return The count of transportations assigned to the employee.
     */
    public long countTransportationsOfEmployee(long employeeId) {
        Employee employee = findEmployeeById(employeeId);
        Set<Transportation> employeeTransportations = employee.getTransportations();
        return employeeTransportations.size();
    }


}
