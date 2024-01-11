package com.example.transportcompany.controller;

import com.example.transportcompany.dto.VehicleTypeDTO;

import com.example.transportcompany.service.VehicleTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicleType")
public class VehicleTypeController {
    private final VehicleTypeService vehicleTypeService;
    @Autowired
    public VehicleTypeController(VehicleTypeService vehicleTypeService) {
        this.vehicleTypeService = vehicleTypeService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> postVehicleType(@RequestBody VehicleTypeDTO vehicleTypeDTO){
        vehicleTypeService.saveVehicleType(vehicleTypeDTO);
        return ResponseEntity.ok("New Vehicle Type added");
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<String> patchVehicleType(@PathVariable Long id, @RequestBody VehicleTypeDTO vehicleTypeDTO){
        try {
            vehicleTypeService.updateVehicleTypeById(id, vehicleTypeDTO);
            return ResponseEntity.ok("The Vehicle Type has been edited");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteVehicleType(@PathVariable Long id){
        try {
            vehicleTypeService.deleteVehicleTypeById(id);
            return ResponseEntity.ok("The Vehicle Type has been deleted");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

