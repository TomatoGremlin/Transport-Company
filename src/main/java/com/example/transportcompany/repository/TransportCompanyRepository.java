package com.example.transportcompany.repository;

import com.example.transportcompany.model.TransportCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransportCompanyRepository extends JpaRepository<TransportCompany, Long> {

   @Query("SELECT c FROM TransportCompany c ORDER BY c.companyName ASC")
   List<TransportCompany> sortAllByNameAscending();
   @Query("SELECT c FROM TransportCompany c WHERE LOWER(c.companyName) LIKE %:name% ORDER BY c.companyName ASC")
   List<TransportCompany> filterByName(String name);

   @Query("SELECT DISTINCT company " +
           "FROM TransportCompany company " +
           "LEFT JOIN company.transportationList t " +
           "LEFT JOIN t.customerList c " +
           "LEFT JOIN t.loadList l " +
           "GROUP BY company.id " +
           "ORDER BY COALESCE(SUM(c.size * l.weight), 0) DESC")
   List<TransportCompany> sortedByRevenue();

   @Query("SELECT DISTINCT company " +
           "FROM TransportCompany company " +
           "LEFT JOIN company.transportationList t " +
           "LEFT JOIN t.customerList c " +
           "LEFT JOIN t.loadList l " +
           "GROUP BY company.id " +
           "HAVING COALESCE(SUM(c.size * l.weight), 0) > :revenueThreshold " +
           "ORDER BY COALESCE(SUM(c.size * l.weight), 0) DESC")
   List<TransportCompany> filteredByRevenueGreaterThan(@Param("revenueThreshold") BigDecimal revenueThreshold);

}
