package com.example.transportcompany.service;

import com.example.transportcompany.dto.EmployeeDTO;
import com.example.transportcompany.model.*;
import com.example.transportcompany.repository.EmployeeRepository;
import com.example.transportcompany.repository.VehicleRepository;
import com.example.transportcompany.repository.VehicleTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepo;
    private final TransportCompanyService companyService;
    private final VehicleRepository vehicleRepo;
    private final VehicleTypeRepository vehicleTypeRepo;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepo,
                           TransportCompanyService companyService,
                           VehicleRepository vehicleRepo,
                           VehicleTypeRepository vehicleTypeRepo) {
        this.employeeRepo = employeeRepo;
        this.companyService = companyService;
        this.vehicleRepo = vehicleRepo;
        this.vehicleTypeRepo = vehicleTypeRepo;
    }

    public void saveEmployee(EmployeeDTO employeeDTO) {
        Employee employeeToSave = new Employee();
        employeeToSave.setName(employeeDTO.getName());
        long companyId = employeeDTO.getCompanyId();
        TransportCompany transportCompany = companyService.findCompanyById(companyId);
        employeeToSave.setCompany(transportCompany);
        employeeToSave.setSalary(employeeDTO.getSalary());
        employeeRepo.save(employeeToSave);
    }
    public void updateEmployeeById(long employeeId, EmployeeDTO updatedEmployee) {
        Employee employeeToUpdate = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));

        String newName = updatedEmployee.getName();
        BigDecimal newSalary = updatedEmployee.getSalary();
        TransportCompany newCompany = companyService.findCompanyById(updatedEmployee.getCompanyId());
        employeeToUpdate.setName(newName);
        employeeToUpdate.setSalary(newSalary);
        employeeToUpdate.setCompany(newCompany);
        employeeRepo.save(employeeToUpdate);
    }
    public void deleteEmployeeById(long id) {
        employeeRepo.deleteById(id);
    }
    public Employee findEmployeeById(long id) {
        return employeeRepo.findById(id).orElse(null);
    }

    public List<Employee> findAllEmployee() {
        return employeeRepo.findAll();
    }


    public void addQualification(long employeeId, long vehicleTypeId) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));
        VehicleType vehicleType = vehicleTypeRepo.findById(vehicleTypeId)
                .orElseThrow(() -> new EntityNotFoundException("VehicleType not found with id: " + vehicleTypeId));
        Set<VehicleType> updatesQualifications = addVehicleTypeToSet(vehicleType, employee);
        employee.setQualifications(updatesQualifications);
        employeeRepo.save(employee);
    }

    public void assignVehicle(long employeeId, long vehicleId) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));
        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with id: " + vehicleId));

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
        List<VehicleType> orderedTypes = vehicleTypeRepo.sortAllByType();
        HashMap<VehicleType,List<Employee>> employeesOrderedByQualification= new HashMap<>();
        for (VehicleType type:orderedTypes) {
            long id = type.getId();
            List<Employee>employees = findByQualification(id);
            employeesOrderedByQualification.put(type, employees);
        }
        return employeesOrderedByQualification;
    }



    public HashMap<Employee, Long> countTransportationsPerEmployee(long companyId) {
        TransportCompany company = companyService.findCompanyById(companyId);
        Set<Employee>employees = company.getEmployees();
        HashMap<Employee, Long> report= new HashMap<>();
        long numberOftransportations;
        for (Employee employee: employees) {
            numberOftransportations= countTransportations(employee.getId());
            report.put(employee, numberOftransportations);
        }
        return report;
    }
    public long countTransportations(long employeeId) {
        Employee employee = findEmployeeById(employeeId);
        Set<Transportation> employeeTransportations = employee.getTransportations();
        return employeeTransportations.size();
    }


}
