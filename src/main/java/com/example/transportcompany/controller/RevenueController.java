package com.example.transportcompany.controller;

import com.example.transportcompany.model.Employee;
import com.example.transportcompany.model.TransportCompany;
import com.example.transportcompany.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/revenue")
public class RevenueController {
    private final RevenueService revenueService;
    @Autowired
    public RevenueController(RevenueService revenueService ) {
        this.revenueService = revenueService;
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<BigDecimal> getRevenue(@PathVariable long companyId){
        BigDecimal transportationsRevenue = revenueService.getRevenueByCompany(companyId);
        return ResponseEntity.ok(transportationsRevenue);
    }

    @GetMapping("/employees/{companyId}")
    public ResponseEntity<HashMap<Employee,BigDecimal>> getRevenueEmployees(@PathVariable long companyId){
        HashMap<Employee,BigDecimal> revenues = revenueService.getRevenueOfEachEmployee(companyId);
        return ResponseEntity.ok(revenues);
    }

    @GetMapping("/timePeriod")
    public ResponseEntity<BigDecimal> getRevenueByPeriod(@RequestParam long companyId, @RequestParam LocalDate from, @RequestParam LocalDate to){
        BigDecimal revenue = revenueService.getRevenueByTimePeriod(companyId, from, to);
        return ResponseEntity.ok(revenue);
    }

    @GetMapping("/sortByRevenue")
    public ResponseEntity<List<TransportCompany>> sortByRevenue(){
        List<TransportCompany>sortedCompanies = revenueService.sortByRevenue();
        return ResponseEntity.ok(sortedCompanies);
    }

    @GetMapping("/filterByRevenueGreaterThan")
    public ResponseEntity<List<TransportCompany>> filterByRevenue(@RequestParam BigDecimal revenue){
        List<TransportCompany>filteredCompanies= revenueService.filterByRevenueGreaterThan(revenue);
        return ResponseEntity.ok(filteredCompanies);
    }

}
