package com.example.transportcompany.controller;
import com.example.transportcompany.dto.VehicleDTO;
import com.example.transportcompany.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;
    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> postVehicle(@RequestBody VehicleDTO vehicleDTO){
        vehicleService.saveVehicle(vehicleDTO);
        return ResponseEntity.ok("New Vehicle added");
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<String> patchVehicle(@PathVariable Long id, @RequestBody VehicleDTO vehicleDTO){
        vehicleService.updateVehicleById(id, vehicleDTO);
        return ResponseEntity.ok("The Vehicle has been edited");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Long id){
        vehicleService.deleteVehicleById(id);
        return ResponseEntity.ok("The Vehicle has been deleted");
    }

}

