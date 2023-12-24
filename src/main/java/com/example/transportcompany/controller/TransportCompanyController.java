package com.example.transportcompany.controller;

import com.example.transportcompany.dto.TransportCompanyDTO;
import com.example.transportcompany.model.TransportCompany;
import com.example.transportcompany.service.TransportCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/company")
public class TransportCompanyController {
    private final TransportCompanyService companyService;
    @Autowired
    public TransportCompanyController(TransportCompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> postCompany(@RequestBody TransportCompanyDTO companyDTO){
        companyService.saveCompany(companyDTO);
        return ResponseEntity.ok("New Company added");
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<String> patchCompany(@PathVariable Long id, @RequestBody TransportCompanyDTO companyDTO){
        companyService.updateCompanyById(id, companyDTO);
        return ResponseEntity.ok("The Company has been edited");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id){
        companyService.deleteCompanyById(id);
        return ResponseEntity.ok("The Company has been deleted");
    }

    @GetMapping("/sortByName")
    public ResponseEntity<List<TransportCompany>> sortByName(){
        List<TransportCompany>sortedCompanies = companyService.sortedByName();
        return ResponseEntity.ok(sortedCompanies);
    }

    @GetMapping("/filterByName/{name}")
    public ResponseEntity<List<TransportCompany>> filterByName(@PathVariable String name){
        List<TransportCompany>filteredCompanies= companyService.filteredByName(name);
        return ResponseEntity.ok(filteredCompanies);
    }

/*
    @GetMapping("/sortByRevenue")
    public ResponseEntity<List<TransportCompany>> sortByRevenue(){
        List<TransportCompany>sortedCompanies = companyService.sortByRevenue();
        return ResponseEntity.ok(sortedCompanies);
    }

    @GetMapping("/filterByRevenue/{revenue}")
    public ResponseEntity<List<TransportCompany>> filterByRevenue(@PathVariable BigDecimal revenue){
        List<TransportCompany>filteredCompanies= companyService.filterByRevenueGreaterThan(revenue);
        return ResponseEntity.ok(filteredCompanies);
    }
*/

}

