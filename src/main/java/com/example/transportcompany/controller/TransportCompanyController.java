package com.example.transportcompany.controller;

import com.example.transportcompany.dto.TransportCompanyDTO;
import com.example.transportcompany.service.TransportCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class TransportCompanyController {
    @Autowired
    private TransportCompanyService companyService;

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

}

