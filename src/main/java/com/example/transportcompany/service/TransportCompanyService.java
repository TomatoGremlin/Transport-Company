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
import java.util.Set;
import java.util.stream.Collectors;

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
        return companyRepo.sortAllByName();
    }
    public List<TransportCompany> filteredByName(String companyName) {
        return companyRepo.findByName(companyName);
    }


    public BigDecimal sumTransportationRevenue(Transportation transportation, TransportationRate companyRates) {
        BigDecimal numberCustomers = BigDecimal.valueOf( getNumberOfCustomers(transportation) );
        BigDecimal totalLoadWeight = BigDecimal.valueOf(sumTotalLoadWeight(transportation));
        BigDecimal loadRate = companyRates.getLoadRate();
        BigDecimal customerRate = companyRates.getCustomerRate();
        return customerRate.multiply(numberCustomers).add(loadRate.multiply(totalLoadWeight));
    }

    public double sumTotalLoadWeight(Transportation transportation){
        Set<Load> loads = transportation.getLoads();
        double totalWeight = 0.0;
        for (Load load: loads) {
            totalWeight+=load.getWeight();
        }
        return totalWeight;
    }

    public int getNumberOfCustomers(Transportation transportation){
        Set<Customer>customers = transportation.getCustomers();
        return customers.size();
    }


    public BigDecimal getRevenueByCompany(TransportCompany company) {
        Set<Transportation> transportations = company.getTransportations();
        TransportationRate rates = company.getTransportationRate();
        BigDecimal companyRevenue= BigDecimal.valueOf(0);
        for (Transportation transportation: transportations) {
            BigDecimal transportationRevenue = sumTransportationRevenue(transportation, rates);
            companyRevenue = companyRevenue.add(transportationRevenue);
        }
        return companyRevenue;
    }

    public List<TransportCompany> sortByRevenue() {
        List<TransportCompany> companies = findAllCompanies();

        // Sort companies based on calculated revenue
        companies.sort(Comparator.comparing(company ->
                getRevenueByCompany((TransportCompany) company)
        ).reversed());
        return companies;
    }

    public List<TransportCompany> findByRevenueGreaterThan(BigDecimal revenueThreshold) {
        List<TransportCompany> companies = findAllCompanies();
        // Filter companies based on revenue greater than the threshold
        List<TransportCompany> filteredCompanies = companies.stream()
                .filter(company -> getRevenueByCompany(company).compareTo(revenueThreshold) > 0)
                .collect(Collectors.toList());

        return filteredCompanies;
    }

}
