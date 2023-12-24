package com.example.transportcompany.repository;

import com.example.transportcompany.model.TransportCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportCompanyRepository extends JpaRepository<TransportCompany, Long> {
   @Query("SELECT c FROM TransportCompany c WHERE LOWER(c.companyName) LIKE %:name% ORDER BY c.companyName ASC")
   List<TransportCompany> findByName(String name);

   @Query("SELECT c FROM TransportCompany c ORDER BY c.companyName ASC")
   List<TransportCompany> sortAllByNameAscending();

}
