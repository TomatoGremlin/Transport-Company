package com.example.transportcompany.controller;

import com.example.transportcompany.model.Employee;
import com.example.transportcompany.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

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

    @GetMapping("/timePeriod/{companyId}/{from}/to/{to}")
    public ResponseEntity<BigDecimal> getRevenueByPeriod(@PathVariable long companyId, @PathVariable LocalDate from, @PathVariable LocalDate to){
        BigDecimal revenue = revenueService.getRevenueByTimePeriod(companyId, from, to);
        return ResponseEntity.ok(revenue);
    }

}
