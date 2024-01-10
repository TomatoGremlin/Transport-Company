package com.example.transportcompany.service;

import com.example.transportcompany.dto.TransportCompanyDTO;
import com.example.transportcompany.model.*;
import com.example.transportcompany.repository.TransportCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransportCompanyService {
    private final TransportCompanyRepository companyRepo;
    private final RevenueService revenueService;
    @Autowired
    public TransportCompanyService(TransportCompanyRepository companyRepo, RevenueService revenueService) {
        this.companyRepo = companyRepo;
        this.revenueService = revenueService;
    }

    public void saveCompany(TransportCompanyDTO companyDTO) {
        TransportCompany companyToSave = new TransportCompany();
        companyToSave.setCompanyName(companyDTO.getCompanyName());
        companyRepo.save(companyToSave);
    }
    public void updateCompanyById(long companyId, TransportCompanyDTO updatedCompany) {
        TransportCompany companyToUpdate = findCompanyById(companyId);

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
        return companyRepo.sortByName();
    }
    public List<TransportCompany> filteredByName(String companyName) {
        return companyRepo.findByName(companyName);
    }


    public List<TransportCompany> sortByRevenue() {
        List<TransportCompany> companies = findAllCompanies();

        // Sort companies based on calculated revenue
        companies.sort(Comparator.comparing(company ->
                revenueService.getRevenueByCompany(((TransportCompany) company).getId())
        ).reversed());
        return companies;
    }

    public List<TransportCompany> findByRevenueGreaterThan(BigDecimal revenueThreshold) {
        List<TransportCompany> companies = findAllCompanies();
        // Filter companies based on revenue greater than the threshold
        List<TransportCompany> filteredCompanies = companies.stream()
                .filter(company -> revenueService.getRevenueByCompany(company.getId()).compareTo(revenueThreshold) > 0)
                .collect(Collectors.toList());

        return filteredCompanies;
    }
}
