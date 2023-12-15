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

    public void saveTransportationRate(TransportationRateDTO transportationRateDTO) {
        TransportationRate transportationRateToSave = new TransportationRate();
        transportationRateToSave.setCustomerRate(transportationRateDTO.getCustomerRate());
        transportationRateToSave.setLoadRate(transportationRateDTO.getLoadRate());

        long companyId = transportationRateDTO.getCompanyId();
        TransportCompany transportCompany = companyService.findCompanyById(companyId);
        transportationRateToSave.setCompany(transportCompany);
        transportationRateRepo.save(transportationRateToSave);
    }
    public void updateTransportationRateById(long transportationRateId, TransportationRateDTO updatedTransportationRate) {
        TransportationRate transportationRateToUpdate = transportationRateRepo.findById(transportationRateId)
                .orElseThrow(() -> new EntityNotFoundException("TransportationRate not found with id: " + transportationRateId));

        BigDecimal newCustomerRate = updatedTransportationRate.getCustomerRate();
        BigDecimal newLoadRate = updatedTransportationRate.getLoadRate();
        TransportCompany newCompany = companyService.findCompanyById(updatedTransportationRate.getCompanyId());

        transportationRateToUpdate.setCustomerRate(newCustomerRate);
        transportationRateToUpdate.setLoadRate(newLoadRate);
        transportationRateToUpdate.setCompany(newCompany);
        transportationRateRepo.save(transportationRateToUpdate);
    }
    public void deleteTransportationRateById(long id) {
        transportationRateRepo.deleteById(id);
    }
    public TransportationRate findTransportationRateById(long id) {
        return transportationRateRepo.findById(id).orElse(null);
    }

    public List<TransportationRate> findAllTransportationRate() {
        return transportationRateRepo.findAll();
    }


}
