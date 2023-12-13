package com.example.transportcompany.repository;

import com.example.transportcompany.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
    // Additional custom query methods can be added here if needed
}
