package com.example.transportcompany.repository;

import com.example.transportcompany.model.Employee;
import com.example.transportcompany.model.Transportation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Long> {
    @Query("SELECT t FROM Transportation t ORDER BY t.endPoint ASC")
    List<Transportation> sortByEndPoint();

    @Query("SELECT t FROM Transportation t WHERE LOWER(t.endPoint) LIKE %:endPoint% ")
    List<Transportation> filterByEndPoint(String endPoint);

    @Query("SELECT COUNT(*) AS total_transportations FROM Transportation")
    long getNumberOfTransportations();

    /*
    @Query("SELECT COALESCE(SUM(l.weight), 0) " +
            "FROM Transportation t " +
            "JOIN t.loadList l " +
            "WHERE t.id = :transportationId")
    double getTotalWeightOfLoadsByTransportationId(@Param("transportationId") long transportationId);

    @Query("SELECT COALESCE(SUM(tr.customerRate * t.customerList.size + tr.loadRate * :loadWeight), 0) " +
            "FROM TransportationRate tr " +
            "JOIN tr.company c " +
            "JOIN c.transportationList t " +
            "WHERE t.id = :transportationId")
    BigDecimal getRevenueOfTransportation(@Param("transportationId") long transportationId, @Param("loadWeight") double loadWeight);

     */
}
