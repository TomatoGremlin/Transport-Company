package com.example.transportcompany.controller;

import com.example.transportcompany.dto.EmployeeDTO;
import com.example.transportcompany.model.Employee;
import com.example.transportcompany.model.VehicleType;
import com.example.transportcompany.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

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


    @GetMapping("/sortByQualification")
    public ResponseEntity<HashMap<VehicleType,List<Employee>>> sortByQualification(){
        HashMap<VehicleType,List<Employee>> sortedEmployees= employeeService.sortByQualification();
        return ResponseEntity.ok(sortedEmployees);
    }

    @GetMapping("/filterByQualification/{qualificationId}")
    public ResponseEntity<List<Employee>> filterByQualification(@PathVariable long qualificationId){
        List<Employee>sortedEmployees = employeeService.findByQualification(qualificationId);
        return ResponseEntity.ok(sortedEmployees);
    }

    @GetMapping("/sortBySalary")
    public ResponseEntity<List<Employee>> sortBySalary(){
        List<Employee>sortedEmployees = employeeService.sortBySalary();
        return ResponseEntity.ok(sortedEmployees);
    }

    @GetMapping("/filterBySalary/{salary}")
    public ResponseEntity<List<Employee>> filterBySalary(@PathVariable BigDecimal salary){
        List<Employee>sortedEmployees = employeeService.findBySalaryGreaterThan(salary);
        return ResponseEntity.ok(sortedEmployees);
    }


    @GetMapping("/all-number-of-transportations/{companyId}")
    public ResponseEntity<HashMap<Employee, Long>> reportNumberTransportationsPerEmployee(@PathVariable long companyId) {
        HashMap<Employee, Long> report= employeeService.countTransportationsPerEmployee(companyId);
        return ResponseEntity.ok(report);
    }
    @GetMapping("/number-of-transportations/{employeeId}")
    public ResponseEntity<Long> reportEmployeeNumberTransportations(@PathVariable long employeeId) {
        long report = employeeService.countTransportations(employeeId);
        return ResponseEntity.ok(report);
    }


}

