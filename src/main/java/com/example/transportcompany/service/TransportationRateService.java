package com.example.transportcompany.service;

import com.example.transportcompany.dto.TransportationRateDTO;
import com.example.transportcompany.model.TransportCompany;
import com.example.transportcompany.model.TransportationRate;
import com.example.transportcompany.repository.TransportationRateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransportationRateService {
    private final TransportationRateRepository transportationRateRepo;
    private final TransportCompanyService companyService;
    @Autowired
    public TransportationRateService(TransportationRateRepository transportationRateRepo,
                                     TransportCompanyService companyService) {
        this.transportationRateRepo = transportationRateRepo;
        this.companyService = companyService;
    }
    /**
     * Saves a new transportation rate based on the provided TransportationRateDTO.
     *
     * @param transportationRateDTO The data transfer object containing transportation rate information.
     */
    public void saveTransportationRate(TransportationRateDTO transportationRateDTO) {
        TransportationRate transportationRateToSave = new TransportationRate();
        transportationRateToSave.setCustomerRate(transportationRateDTO.getCustomerRate());
        transportationRateToSave.setLoadRate(transportationRateDTO.getLoadRate());

        long companyId = transportationRateDTO.getCompanyId();
        TransportCompany transportCompany = companyService.findCompanyById(companyId);
        transportationRateToSave.setCompany(transportCompany);
        transportationRateRepo.save(transportationRateToSave);
    }
    /**
     * Updates an existing transportation rate by ID using the provided TransportationRateDTO.
     *
     * @param transportationRateId        The ID of the transportation rate to update.
     * @param updatedTransportationRate The data transfer object containing updated transportation rate information.
     */
    public void updateTransportationRateById(long transportationRateId, TransportationRateDTO updatedTransportationRate) {
        TransportationRate transportationRateToUpdate = findTransportationRateById(transportationRateId);
        BigDecimal newCustomerRate = updatedTransportationRate.getCustomerRate();
        BigDecimal newLoadRate = updatedTransportationRate.getLoadRate();
        TransportCompany newCompany = companyService.findCompanyById(updatedTransportationRate.getCompanyId());

        transportationRateToUpdate.setCustomerRate(newCustomerRate);
        transportationRateToUpdate.setLoadRate(newLoadRate);
        transportationRateToUpdate.setCompany(newCompany);
        transportationRateRepo.save(transportationRateToUpdate);
    }


    /**
     * Deletes a transportation rate by ID.
     *
     * @param id The ID of the transportation rate to be deleted.
     * @throws EntityNotFoundException If the rate with the specified ID is not found.
     */
    public void deleteTransportationRateById(long id) {
            findTransportationRateById(id);
            transportationRateRepo.deleteById(id);
    }
    /**
     * Finds a transportation rate by its ID.
     *
     * @param id The ID of the transportation rate to retrieve.
     * @return The TransportationRate object corresponding to the given ID.
     * @throws EntityNotFoundException If the rate with the specified ID is not found.
     */
    public TransportationRate findTransportationRateById(long id) {
        return transportationRateRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Rate not found with id: " + id));
    }

    /**
     * Retrieves a list of all transportation rates.
     *
     * @return A list containing all transportation rates.
     */
    public List<TransportationRate> findAllTransportationRate() {
        return transportationRateRepo.findAll();
    }

}
