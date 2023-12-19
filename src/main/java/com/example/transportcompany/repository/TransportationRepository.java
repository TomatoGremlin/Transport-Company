package com.example.transportcompany.repository;

import com.example.transportcompany.model.Employee;
import com.example.transportcompany.model.Transportation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Long> {
    @Query("SELECT t FROM Transportation t ORDER BY t.endPoint ASC")
    List<Transportation> sortByEndPoint();

    @Query("SELECT t FROM Transportation t WHERE LOWER(t.endPoint) LIKE %:endPoint% ")
    List<Transportation> filterByEndPoint(String endPoint);
}
