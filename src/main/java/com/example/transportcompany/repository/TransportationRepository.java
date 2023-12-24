package com.example.transportcompany.repository;

import com.example.transportcompany.model.Transportation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Long> {
    List<Transportation> findByCompanyId(Long companyId);
    List<Transportation> findByEmployeeId(Long employeeId);
    @Query("SELECT t FROM Transportation t WHERE LOWER(t.endPoint) LIKE %:destination% OR LOWER(t.startPoint) LIKE %:destination%")
    List<Transportation> findByDestination(String destination);
    @Query("SELECT t FROM Transportation t ORDER BY t.endPoint ASC")
    List<Transportation> sortByEndPoint();
    @Query("SELECT COUNT(*) AS total_transportations FROM Transportation WHERE company.id = :companyId")
    long getCountByCompanyId(long companyId);
    @Query("SELECT t FROM Transportation t WHERE t.company.id = :companyId AND t.departureDate BETWEEN :startDate AND :endDate")
    List<Transportation>findByPeriodAndCompany(long companyId, LocalDate startDate, LocalDate endDate);


}
