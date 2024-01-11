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

    /**
     * Saves a new vehicle type based on the provided VehicleTypeDTO.
     *
     * @param vehicleTypeDTO The data transfer object containing vehicle type information.
     */
    public void saveVehicleType(VehicleTypeDTO vehicleTypeDTO) {
        VehicleType vehicleTypeToSave = new VehicleType();
        vehicleTypeToSave.setType(vehicleTypeDTO.getType());
        vehicleTypeRepo.save(vehicleTypeToSave);
    }
    /**
     * Updates an existing vehicle type by ID using the provided VehicleTypeDTO.
     *
     * @param vehicleTypeId       The ID of the vehicle type to update.
     * @param updatedVehicleType The data transfer object containing updated vehicle type information.
     * @throws EntityNotFoundException If the vehicle type with the specified ID is not found.
     */
    public void updateVehicleTypeById(long vehicleTypeId, VehicleTypeDTO updatedVehicleType) {
        VehicleType vehicleTypeToUpdate = findVehicleTypeById(vehicleTypeId);
        String newType = updatedVehicleType.getType();
        vehicleTypeToUpdate.setType(newType);
        vehicleTypeRepo.save(vehicleTypeToUpdate);
    }
    /**
     * Deletes a vehicle type by ID.
     *
     * @param id The ID of the vehicle type to be deleted.
     * @throws EntityNotFoundException If the vehicle type with the specified ID is not found.
     */

    public void deleteVehicleTypeById(long id) {
        findVehicleTypeById(id);
        vehicleTypeRepo.deleteById(id);
    }
    /**
     * Finds a vehicle type by its ID.
     *
     * @param id The ID of the vehicle type to retrieve.
     * @return The VehicleType object corresponding to the given ID.
     * @throws EntityNotFoundException If the vehicle type with the specified ID is not found.
     */

    public VehicleType findVehicleTypeById(long id) {
        return vehicleTypeRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Vehicle Type not found with id: " + id));
    }
    /**
     * Retrieves a list of all vehicle types.
     *
     * @return A list containing all vehicle types.
     */
    public List<VehicleType> findAllVehicleTypes() {
        return vehicleTypeRepo.findAll();
    }


    /**
     * Retrieves a sorted list of vehicle types by type.
     *
     * @return A sorted list of vehicle types.
     */
    public List<VehicleType> sortByType(){
        return vehicleTypeRepo.sortByType();
    }
}
