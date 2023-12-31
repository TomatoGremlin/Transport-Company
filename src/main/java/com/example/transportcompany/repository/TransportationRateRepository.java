package com.example.transportcompany.repository;

import com.example.transportcompany.model.TransportationRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransportationRateRepository extends JpaRepository<TransportationRate, Long> {
    TransportationRate findByCompanyId(Long companyId);
}
