package com.example.transportcompany.service;

import com.example.transportcompany.dto.TransportCompanyDTO;
import com.example.transportcompany.model.TransportCompany;
import com.example.transportcompany.repository.TransportCompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransportCompanyService {
    private final TransportCompanyRepository companyRepo;
    @Autowired
    public TransportCompanyService(TransportCompanyRepository companyRepo) {
        this.companyRepo = companyRepo;
    }


    public void saveCompany(TransportCompanyDTO companyDTO) {
        TransportCompany companyToSave = new TransportCompany();
        companyToSave.setCompanyName(companyDTO.getCompanyName());
        companyRepo.save(companyToSave);
    }
    public void updateCompanyById(long companyId, TransportCompanyDTO updatedCompany) {
        TransportCompany companyToUpdate = companyRepo.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + companyId));

        String newName = updatedCompany.getCompanyName();
        companyToUpdate.setCompanyName(newName);
        companyRepo.save(companyToUpdate);
    }

    public void deleteCompanyById(long id) {
        companyRepo.deleteById(id);
    }
    public TransportCompany findCompanyById(long id) {
        return companyRepo.findById(id).orElse(null);
    }

    public List<TransportCompany> findAllCompanies() {
        return companyRepo.findAll();
    }

    public List<TransportCompany> sortedByName() {
        return companyRepo.sortAllByNameAscending();
    }
    public List<TransportCompany> filteredByName(String companyName) {
        return companyRepo.filterByName(companyName);
    }


    public List<TransportCompany> sortByRevenue() {
        return companyRepo.sortedByRevenue();
    }
    public List<TransportCompany> filterByRevenueGreaterThan(BigDecimal revenue) {
        return companyRepo.filteredByRevenueGreaterThan(revenue);
    }

}
