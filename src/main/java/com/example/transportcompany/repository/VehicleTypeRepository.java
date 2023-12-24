package com.example.transportcompany.repository;

import com.example.transportcompany.model.TransportCompany;
import com.example.transportcompany.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
    @Query("SELECT v FROM VehicleType v ORDER BY v.type")
    List<VehicleType> sortAllByType();

}
