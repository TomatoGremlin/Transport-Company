package com.example.transportcompany.service;


import com.example.transportcompany.dto.VehicleDTO;

import com.example.transportcompany.model.TransportCompany;
import com.example.transportcompany.model.Vehicle;
import com.example.transportcompany.model.VehicleType;
import com.example.transportcompany.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepo;
    @Autowired
    private TransportCompanyService companyService;
    @Autowired
    private VehicleTypeService vehicleTypeService;

    public void saveVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicleToSave = new Vehicle();
        long vehicleTypeId = vehicleDTO.getVehicleTypeId();
        VehicleType vehicleType = vehicleTypeService.findVehicleTypeById(vehicleTypeId);
        vehicleToSave.setVehicleType(vehicleType);
        long companyId = vehicleDTO.getCompanyId();
        TransportCompany transportCompany = companyService.findCompanyById(companyId);
        vehicleToSave.setCompany(transportCompany);
        vehicleRepo.save(vehicleToSave);
    }
    public void updateVehicleById(long vehicleId, VehicleDTO updatedVehicle) {
        Vehicle vehicleToUpdate = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with id: " + vehicleId));

        //is there a point of changing the type
        VehicleType newType = vehicleTypeService.findVehicleTypeById(updatedVehicle.getVehicleTypeId());
        vehicleToUpdate.setVehicleType(newType);

        TransportCompany newCompany = companyService.findCompanyById(updatedVehicle.getCompanyId());
        vehicleToUpdate.setCompany(newCompany);
        vehicleToUpdate.setCompany(newCompany);
        vehicleRepo.save(vehicleToUpdate);
    }
    public void deleteVehicleById(long id) {
        vehicleRepo.deleteById(id);
    }
    public Vehicle findVehicleById(long id) {
        return vehicleRepo.findById(id).orElse(null);
    }

    public List<Vehicle> findAllVehicle() {
        return vehicleRepo.findAll();
    }


}
