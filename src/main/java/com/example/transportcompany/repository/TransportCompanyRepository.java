package com.example.transportcompany.repository;

import com.example.transportcompany.model.TransportCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TransportCompanyRepository extends JpaRepository<TransportCompany, Long> {
}
