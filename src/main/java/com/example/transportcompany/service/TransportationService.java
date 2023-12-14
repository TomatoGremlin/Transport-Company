package com.example.transportcompany.service;

import com.example.transportcompany.dto.EmployeeDTO;
import com.example.transportcompany.dto.TransportCompanyDTO;
import com.example.transportcompany.dto.TransportationDTO;
import com.example.transportcompany.model.Employee;
import com.example.transportcompany.model.TransportCompany;
import com.example.transportcompany.model.Transportation;
import com.example.transportcompany.repository.EmployeeRepository;
import com.example.transportcompany.repository.TransportationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransportationService {
    @Autowired
    private TransportationRepository transportationRepo;
    @Autowired
    private TransportCompanyService companyService;

    public void saveTransportation(TransportationDTO transportationDTO) {
        Transportation transportationToSave = new Transportation();
        transportationToSave.setStartPoint(transportationToSave.getStartPoint());
        transportationToSave.setEndPoint(transportationToSave.getEndPoint());
        transportationToSave.setDepartureDate(transportationDTO.getDepartureDate());
        transportationToSave.setArrivalDate(transportationDTO.getArrivalDate());
        transportationToSave.setPaymentStatus(transportationDTO.isPaymentStatus());

        long companyId = transportationDTO.getCompanyId();
        TransportCompany transportCompany = companyService.findCompanyById(companyId);
        transportationToSave.setCompany(transportCompany);
        transportationRepo.save(transportationToSave);
    }
    public void updateTransportationById(long transportationId, TransportationDTO updatedTransportation) {
        Transportation transportationToUpdate = transportationRepo.findById(transportationId)
                .orElseThrow(() -> new EntityNotFoundException("Transportation not found with id: " + transportationId));

        String newStartPoint = updatedTransportation.getStartPoint();
        String newEndPoint = updatedTransportation.getEndPoint();
        LocalDate newDepartureDate = updatedTransportation.getDepartureDate();
        LocalDate newArrivalDate =  updatedTransportation.getArrivalDate();
        boolean newPaymentStatus = updatedTransportation.isPaymentStatus();

        TransportCompany newCompany = companyService.findCompanyById(updatedTransportation.getCompanyId());
        transportationToUpdate.setDepartureDate(newDepartureDate);
        transportationToUpdate.setArrivalDate(newArrivalDate);
        transportationToUpdate.setStartPoint(newStartPoint);
        transportationToUpdate.setEndPoint(newEndPoint);
        transportationToUpdate.setPaymentStatus(newPaymentStatus);
        transportationToUpdate.setCompany(newCompany);
        transportationRepo.save(transportationToUpdate);
    }
    public void deleteTransportationById(long id) {
        transportationRepo.deleteById(id);
    }
    public Transportation findTransportationById(long id) {
        return transportationRepo.findById(id).orElse(null);
    }

    public List<Transportation> findAllTransportation() {
        return transportationRepo.findAll();
    }


}
