package com.example.transportcompany.controller;

import com.example.transportcompany.dto.TransportCompanyDTO;
import com.example.transportcompany.model.TransportCompany;
import com.example.transportcompany.service.TransportCompanyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        try {
            companyService.updateCompanyById(id, companyDTO);
            return ResponseEntity.ok("The Company has been edited");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id){
        try {
            companyService.deleteCompanyById(id);
            return ResponseEntity.ok("The Company has been deleted");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/sortByName")
    public ResponseEntity<List<TransportCompany>> sortByName(){
        List<TransportCompany>sortedCompanies = companyService.sortByName();
        return ResponseEntity.ok(sortedCompanies);
    }

    @GetMapping("/filterByName")
    public ResponseEntity<List<TransportCompany>> filterByName(@RequestParam String name){
        List<TransportCompany>filteredCompanies= companyService.filteredByName(name);
        return ResponseEntity.ok(filteredCompanies);
    }



}

