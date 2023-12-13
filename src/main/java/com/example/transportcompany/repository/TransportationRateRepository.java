package com.example.transportcompany.repository;

import com.example.transportcompany.model.TransportationRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TransportationRateRepository extends JpaRepository<TransportationRate, Long> {
    // Additional custom query methods can be added here if needed
}
