package com.example.transportcompany.service;

import com.example.transportcompany.dto.EmployeeDTO;
import com.example.transportcompany.dto.TransportCompanyDTO;
import com.example.transportcompany.model.Employee;
import com.example.transportcompany.model.TransportCompany;
import com.example.transportcompany.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepo;
    @Autowired
    private TransportCompanyService companyService;

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


}
