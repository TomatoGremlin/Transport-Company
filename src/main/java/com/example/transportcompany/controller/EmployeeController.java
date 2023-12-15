package com.example.transportcompany.controller;

import com.example.transportcompany.dto.EmployeeDTO;
import com.example.transportcompany.dto.TransportCompanyDTO;
import com.example.transportcompany.service.EmployeeService;
import com.example.transportcompany.service.TransportCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> postEmployee(@RequestBody EmployeeDTO employeeDTO){
        employeeService.saveEmployee(employeeDTO);
        return ResponseEntity.ok("New Employee added");
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<String> patchEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO){
        employeeService.updateEmployeeById(id, employeeDTO);
        return ResponseEntity.ok("The Employee has been edited");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok("The Employee has been deleted");
    }

    @PutMapping("/{employeeId}/add-driver-qualification/{vehicleTypeId}")
    public ResponseEntity<String> addQualification(@PathVariable long employeeId, @PathVariable long vehicleTypeId){
        employeeService.addQualification(employeeId, vehicleTypeId);
        return ResponseEntity.ok("The Driver Qualification has been added to the Employee");
    }
    @PutMapping("/{employeeId}/assign-vehicle/{vehicleId}")
    public ResponseEntity<String> assignVehicle(@PathVariable long employeeId, @PathVariable long vehicleId){
        employeeService.assignVehicle(employeeId, vehicleId);
        return ResponseEntity.ok("The Vehicle has been assigned to the Employee");
    }


}

