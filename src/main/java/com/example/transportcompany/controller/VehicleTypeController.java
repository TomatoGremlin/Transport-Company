package com.example.transportcompany.controller;

import com.example.transportcompany.dto.VehicleTypeDTO;

import com.example.transportcompany.service.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
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
        vehicleTypeService.updateVehicleTypeById(id, vehicleTypeDTO);
        return ResponseEntity.ok("The Vehicle Type has been edited");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteVehicleType(@PathVariable Long id){
        vehicleTypeService.deleteVehicleTypeById(id);
        return ResponseEntity.ok("The Vehicle Type has been deleted");
    }

}

