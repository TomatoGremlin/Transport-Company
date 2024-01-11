package com.example.transportcompany.controller;

import com.example.transportcompany.dto.LoadDTO;
import com.example.transportcompany.service.LoadService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/load")
public class LoadController {
    private final LoadService loadService;
    @Autowired
    public LoadController(LoadService loadService) {
        this.loadService = loadService;
    }


    @PostMapping("/add")
    public ResponseEntity<String> postLoad(@RequestBody LoadDTO loadDTO){
        loadService.saveLoad(loadDTO);
        return ResponseEntity.ok("New Load added");
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<String> patchLoad(@PathVariable Long id, @RequestBody LoadDTO loadDTO){
        try {
            loadService.updateLoadById(id, loadDTO);
            return ResponseEntity.ok("The Load has been edited");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteLoad(@PathVariable Long id){
        try {
            loadService.deleteLoadById(id);
            return ResponseEntity.ok("The Load has been deleted");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

