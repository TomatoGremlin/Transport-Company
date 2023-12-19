package com.example.transportcompany.controller;

import com.example.transportcompany.dto.TransportationDTO;
import com.example.transportcompany.model.Transportation;
import com.example.transportcompany.service.TransportationService;
import com.example.transportcompany.util.TransportationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transportation")
public class TransportationController {
    private final TransportationService transportationService;

    @Autowired
    public TransportationController(TransportationService transportationService ) {
        this.transportationService = transportationService;
    }

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTransportation(@PathVariable Long id){
        transportationService.deleteTransportationById(id);
        return ResponseEntity.ok("The Transportation has been deleted");
    }

    @PutMapping("/{transportationId}/add-customer/{customerId}")
    public ResponseEntity<String> addCustomer(@PathVariable long transportationId, @PathVariable long customerId){
        transportationService.addCustomer(transportationId, customerId);
        return ResponseEntity.ok("The Customer has been added to the Transportation");
    }
    @PutMapping("/{transportationId}/add-load/{loadId}")
    public ResponseEntity<String> addLoad(@PathVariable long transportationId, @PathVariable long loadId){
        transportationService.addLoad(transportationId, loadId);
        return ResponseEntity.ok("The Load has been added to the Transportation");
    }

    @PutMapping("/{transportationId}/assign-employee/{employeeId}")
    public ResponseEntity<String> assignEmployee(@PathVariable long transportationId, @PathVariable long employeeId){
        transportationService.assignEmployee(transportationId, employeeId);
        return ResponseEntity.ok("The Employee has been assigned to the Transportation");
    }

    @GetMapping("/sortByDestination")
    public ResponseEntity<List<Transportation>> sortByDestination(){
        List<Transportation>sortedDestinations = transportationService.sortByDestination();
        return ResponseEntity.ok(sortedDestinations);
    }

    @GetMapping("/filterByDestination/{destination}")
    public ResponseEntity<List<Transportation>> filterByDestination(@PathVariable String destination){
        List<Transportation>filteredTransportations= transportationService.filterByDestination(destination);
        return ResponseEntity.ok(filteredTransportations);
    }

    @PostMapping("/writeToFile/{fileName}")
    public ResponseEntity<String> writeTransportationInfoToFile(@PathVariable String fileName) {
        transportationService.writeToFile(fileName);
        return ResponseEntity.ok("Transportations have been written to file");

    }
    @GetMapping("/retrieveFromFile/{fileName}")
    public ResponseEntity<String> getAllTransportationInfoFromFile(@PathVariable String fileName) {
        String info = transportationService.readFromFile(fileName);
        return ResponseEntity.ok(info);
    }

}

