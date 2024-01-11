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
    private final VehicleRepository vehicleRepo;
    private final TransportCompanyService companyService;
    private final VehicleTypeService vehicleTypeService;
    @Autowired
    public VehicleService(VehicleRepository vehicleRepo,
                          TransportCompanyService companyService,
                          VehicleTypeService vehicleTypeService) {
        this.vehicleRepo = vehicleRepo;
        this.companyService = companyService;
        this.vehicleTypeService = vehicleTypeService;
    }
    /**
     * Saves a new vehicle based on the provided VehicleDTO.
     *
     * @param vehicleDTO The data transfer object containing vehicle information.
     */
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
    /**
     * Updates an existing vehicle by ID using the provided VehicleDTO.
     *
     * @param vehicleId     The ID of the vehicle to update.
     * @param updatedVehicle The data transfer object containing updated vehicle information.
     * @throws EntityNotFoundException If the vehicle with the specified ID is not found.
     */
    public void updateVehicleById(long vehicleId, VehicleDTO updatedVehicle) {
        Vehicle vehicleToUpdate = findVehicleById(vehicleId);

        VehicleType newType = vehicleTypeService.findVehicleTypeById(updatedVehicle.getVehicleTypeId());
        TransportCompany newCompany = companyService.findCompanyById(updatedVehicle.getCompanyId());
        vehicleToUpdate.setVehicleType(newType);
        vehicleToUpdate.setCompany(newCompany);
        vehicleToUpdate.setCompany(newCompany);
        vehicleRepo.save(vehicleToUpdate);
    }
    /**
     * Deletes a vehicle by ID.
     *
     * @param id The ID of the vehicle to be deleted.
     * @throws EntityNotFoundException If the vehicle with the specified ID is not found.
     */
    public void deleteVehicleById(long id) {
        findVehicleById(id);
        vehicleRepo.deleteById(id);
    }
    /**
     * Finds a vehicle by its ID.
     *
     * @param id The ID of the vehicle to retrieve.
     * @return The Vehicle object corresponding to the given ID.
     * @throws EntityNotFoundException If the vehicle with the specified ID is not found.
     */
    public Vehicle findVehicleById(long id) {
        return vehicleRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Vehicle not found with id: " + id));
    }
    /**
     * Retrieves a list of all vehicles.
     *
     * @return A list containing all vehicles.
     */
    public List<Vehicle> findAllVehicle() {
        return vehicleRepo.findAll();
    }

}
