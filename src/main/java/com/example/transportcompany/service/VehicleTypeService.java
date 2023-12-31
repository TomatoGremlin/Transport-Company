package com.example.transportcompany.service;

import com.example.transportcompany.dto.VehicleTypeDTO;
import com.example.transportcompany.model.VehicleType;
import com.example.transportcompany.repository.VehicleTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleTypeService {
    private final VehicleTypeRepository vehicleTypeRepo;
    @Autowired
    public VehicleTypeService(VehicleTypeRepository vehicleTypeRepo) {
        this.vehicleTypeRepo = vehicleTypeRepo;
    }


    public void saveVehicleType(VehicleTypeDTO vehicleTypeDTO) {
        VehicleType vehicleTypeToSave = new VehicleType();
        vehicleTypeToSave.setType(vehicleTypeDTO.getType());
        vehicleTypeRepo.save(vehicleTypeToSave);
    }
    public void updateVehicleTypeById(long vehicleTypeId, VehicleTypeDTO updatedVehicleType) {
        VehicleType vehicleTypeToUpdate = vehicleTypeRepo.findById(vehicleTypeId)
                .orElseThrow(() -> new EntityNotFoundException("VehicleType not found with id: " + vehicleTypeId));

        String newType = updatedVehicleType.getType();
        vehicleTypeToUpdate.setType(newType);
        vehicleTypeRepo.save(vehicleTypeToUpdate);
    }
    public void deleteVehicleTypeById(long id) {
        vehicleTypeRepo.deleteById(id);
    }
    public VehicleType findVehicleTypeById(long id) {
        return vehicleTypeRepo.findById(id).orElse(null);
    }

    public List<VehicleType> findAllVehicleType() {
        return vehicleTypeRepo.findAll();
    }


}
