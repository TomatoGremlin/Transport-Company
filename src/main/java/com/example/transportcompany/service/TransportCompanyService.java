package com.example.transportcompany.service;

import com.example.transportcompany.dto.TransportCompanyDTO;
import com.example.transportcompany.model.*;
import com.example.transportcompany.repository.TransportCompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransportCompanyService {
    private final TransportCompanyRepository companyRepo;
    @Autowired
    public TransportCompanyService(TransportCompanyRepository companyRepo) {
        this.companyRepo = companyRepo;
    }

    /**
     * Saves a new transport company based on the provided TransportCompanyDTO.
     *
     * @param companyDTO The data transfer object containing transport company information.
     */
    public void saveCompany(TransportCompanyDTO companyDTO) {
        TransportCompany companyToSave = new TransportCompany();
        companyToSave.setCompanyName(companyDTO.getCompanyName());
        companyRepo.save(companyToSave);
    }

    /**
     * Updates an existing transport company by ID using the provided TransportCompanyDTO.
     *
     * @param companyId     The ID of the transport company to update.
     * @param updatedCompany The data transfer object containing updated transport company information.
     * @throws EntityNotFoundException If the transport company with the specified ID is not found.
     */
    public void updateCompanyById(long companyId, TransportCompanyDTO updatedCompany) {
        TransportCompany companyToUpdate = findCompanyById(companyId);

        String newName = updatedCompany.getCompanyName();
        companyToUpdate.setCompanyName(newName);
        companyRepo.save(companyToUpdate);
    }
    /**
     * Deletes a transport company by ID.
     *
     * @param id The ID of the transport company to be deleted.
     * @throws EntityNotFoundException If the transport company with the specified ID is not found.
     */
    public void deleteCompanyById(long id) {
        findCompanyById(id);
        companyRepo.deleteById(id);
    }
    /**
     * Finds a transport company by its ID.
     *
     * @param id The ID of the transport company to retrieve.
     * @return The TransportCompany object corresponding to the given ID.
     * @throws EntityNotFoundException If the transport company with the specified ID is not found.
     */
    public TransportCompany findCompanyById(long id) {
        return companyRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + id));
    }
    /**
     * Retrieves a list of all transport companies.
     *
     * @return A list containing all transport companies.
     */
    public List<TransportCompany> findAllCompanies() {
        return companyRepo.findAll();
    }


    /**
     * Retrieves a list of transport companies filtered by name.
     *
     * @param companyName The name used to filter transport companies.
     * @return A list of transport companies matching the specified name.
     */
    public List<TransportCompany> filteredByName(String companyName) {
        return companyRepo.findByName(companyName);
    }

    /**
     * Sorts the list of transport companies by name.
     *
     * @return A list of transport companies sorted by name.
     */
    public List<TransportCompany> sortByName() {
        return companyRepo.sortByName();
    }

}
