package com.example.transportcompany.service;

import com.example.transportcompany.dto.EmployeeDTO;
import com.example.transportcompany.model.*;
import com.example.transportcompany.repository.EmployeeRepository;
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

    public void saveEmployee(EmployeeDTO employeeDTO) {
        Employee employeeToSave = new Employee();
        employeeToSave.setName(employeeDTO.getName());
        employeeToSave.setSalary(employeeDTO.getSalary());
        long companyId = employeeDTO.getCompanyId();
        TransportCompany transportCompany = transportCompanyService.findCompanyById(companyId);
        employeeToSave.setCompany(transportCompany);
        employeeRepo.save(employeeToSave);
    }
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
    public void deleteEmployeeById(long id) {
        employeeRepo.deleteById(id);
    }

    public void addQualification(long employeeId, long vehicleTypeId) {
        Employee employee = findEmployeeById(employeeId);
        VehicleType vehicleType = vehicleTypeService.findVehicleTypeById(vehicleTypeId);
        Set<VehicleType> updatesQualifications = addVehicleTypeToSet(vehicleType, employee);
        employee.setQualifications(updatesQualifications);
        employeeRepo.save(employee);
    }

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


    public boolean doesVehicleQualificationMatch(Employee employee, Vehicle vehicle){
       Set<VehicleType> driverQualifications = employee.getQualifications();
       VehicleType vehicleType = vehicle.getVehicleType();
       return driverQualifications.contains(vehicleType);
    }

    public Set<Vehicle> addVehicleToSet(Vehicle vehicle, Employee employee){
        Set<Vehicle> currentVehicles = employee.getVehicles();
        currentVehicles.add(vehicle);
        return currentVehicles;
    }

    public Set<VehicleType> addVehicleTypeToSet(VehicleType vehicleType, Employee employee){
        Set<VehicleType> currentQualifications = employee.getQualifications();
        currentQualifications.add(vehicleType);
        return currentQualifications;
    }
    public Employee findEmployeeById(long id) {
        return employeeRepo.findById(id).orElse(null);
    }

    public List<Employee> findAllEmployee() {
        return employeeRepo.findAll();
    }

    public List<Employee>findByCompany(long companyId){
        return employeeRepo.findByCompanyId(companyId);
    }

    public List<Employee> findBySalaryGreaterThan(BigDecimal salary) {
        return employeeRepo.filteredBySalaryGreaterThan(salary);
    }
    public List<Employee> findByQualification(long qualificationId) {
        return employeeRepo.filteredByQualification(qualificationId);
    }
    public List<Employee> sortBySalary() {
        return employeeRepo.sortedBySalary();
    }

    public HashMap<VehicleType,List<Employee>> sortByQualification() {
        List<VehicleType> orderedTypes = vehicleTypeService.orderByType();
        return orderedTypes.stream()
                .collect(Collectors.toMap(
                        type -> type,
                        type -> findByQualification(type.getId()),
                        (existing, replacement) -> existing,
                        HashMap::new
                ));
    }


    public HashMap<Employee, Long> countTransportationsPerEmployee(long companyId) {
        TransportCompany company = transportCompanyService.findCompanyById(companyId);
        Set<Employee> employees = company.getEmployees();
        return employees.stream()
                .collect(Collectors.toMap(
                        employee -> employee,
                        employee -> countTransportations(employee.getId()),
                        (existing, replacement) -> existing,
                        HashMap::new
                ));
    }
    public long countTransportations(long employeeId) {
        Employee employee = findEmployeeById(employeeId);
        Set<Transportation> employeeTransportations = employee.getTransportations();
        return employeeTransportations.size();
    }


}
