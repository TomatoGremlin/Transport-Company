package com.example.transportcompany.controller;

import com.example.transportcompany.dto.EmployeeDTO;
import com.example.transportcompany.dto.TransportCompanyDTO;
import com.example.transportcompany.dto.TransportationDTO;
import com.example.transportcompany.service.EmployeeService;
import com.example.transportcompany.service.TransportCompanyService;
import com.example.transportcompany.service.TransportationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transportation")
public class TransportationController {
    @Autowired
    private TransportationService transportationService;

    @PostMapping("/add")
    public ResponseEntity<String> postTransportation(@RequestBody TransportationDTO transportationDTO){
        transportationService.saveTransportation(transportationDTO);
        return ResponseEntity.ok("New Transportation added");
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<String> patchTransportation(@PathVariable Long id, @RequestBody TransportationDTO transportationDTO){
        transportationService.updateTransportationById(id, transportationDTO);
        return ResponseEntity.ok("The Transportation has been edited");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteTransportation(@PathVariable Long id){
        transportationService.deleteTransportationById(id);
        return ResponseEntity.ok("The Transportation has been deleted");
    }

}

