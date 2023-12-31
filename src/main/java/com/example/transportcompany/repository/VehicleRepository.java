package com.example.transportcompany.repository;

import com.example.transportcompany.model.TransportationRate;
import com.example.transportcompany.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByCompanyId(Long companyId);

}
