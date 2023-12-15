package com.example.transportcompany.controller;

import com.example.transportcompany.dto.TransportationRateDTO;
import com.example.transportcompany.service.TransportationRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transportationRate")
public class TransportationRateController {
    private final TransportationRateService transportationRateService;
    @Autowired
    public TransportationRateController(TransportationRateService transportationRateService) {
        this.transportationRateService = transportationRateService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> postTransportationRate(@RequestBody TransportationRateDTO transportationRateDTO){
        transportationRateService.saveTransportationRate(transportationRateDTO);
        return ResponseEntity.ok("New Transportation Rate added");
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<String> patchTransportationRate(@PathVariable Long id, @RequestBody TransportationRateDTO transportationRateDTO){
        transportationRateService.updateTransportationRateById(id, transportationRateDTO);
        return ResponseEntity.ok("The Transportation Rate has been edited");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTransportationRate(@PathVariable Long id){
        transportationRateService.deleteTransportationRateById(id);
        return ResponseEntity.ok("The Transportation Rate has been deleted");
    }

}

