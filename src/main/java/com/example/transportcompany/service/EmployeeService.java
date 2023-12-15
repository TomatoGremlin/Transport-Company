package com.example.transportcompany.service;

import com.example.transportcompany.dto.EmployeeDTO;
import com.example.transportcompany.model.*;
import com.example.transportcompany.repository.EmployeeRepository;
import com.example.transportcompany.repository.VehicleRepository;
import com.example.transportcompany.repository.VehicleTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {
    @Autowired
    private  EmployeeRepository employeeRepo;
    @Autowired
    private  TransportCompanyService companyService;
    @Autowired
    private VehicleRepository vehicleRepo;
    @Autowired
    private VehicleTypeRepository vehicleTypeRepo;


    public void saveEmployee(EmployeeDTO employeeDTO) {
        Employee employeeToSave = new Employee();
        employeeToSave.setName(employeeDTO.getName());
        long companyId = employeeDTO.getCompanyId();
        TransportCompany transportCompany = companyService.findCompanyById(companyId);
        employeeToSave.setCompany(transportCompany);
        employeeRepo.save(employeeToSave);
    }
    public void updateEmployeeById(long employeeId, EmployeeDTO updatedEmployee) {
        Employee employeeToUpdate = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));

        String newName = updatedEmployee.getName();
        TransportCompany newCompany = companyService.findCompanyById(updatedEmployee.getCompanyId());
        employeeToUpdate.setName(newName);
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
        employee.setVehicleTypeList(updatesQualifications);
        employeeRepo.save(employee);
    }

    public void assignVehicle(long employeeId, long vehicleId) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));
        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with id: " + vehicleId));

        if(!checkVehicleQualificationMatch(employee, vehicle)){
            throw new RuntimeException("The Employee holds no qualification to drive the Vehicle.");
        }
        Set<Vehicle> updatedVehicles = addVehicleToSet(vehicle, employee);
        employee.setVehicleList(updatedVehicles);
        employeeRepo.save(employee);
    }


    public boolean checkVehicleQualificationMatch (Employee employee, Vehicle vehicle){
       Set<VehicleType> driverQualifications = employee.getVehicleTypeList();
       VehicleType vehicleType = vehicle.getVehicleType();
       return driverQualifications.contains(vehicleType);
    }

    public Set<Vehicle> addVehicleToSet(Vehicle vehicle, Employee employee){
        Set<Vehicle> currentVehicles = employee.getVehicleList();
        currentVehicles.add(vehicle);
        return currentVehicles;
    }
    public Set<VehicleType> addVehicleTypeToSet(VehicleType vehicleType, Employee employee){
        Set<VehicleType> currentQualifications = employee.getVehicleTypeList();
        currentQualifications.add(vehicleType);
        return currentQualifications;
    }
}
