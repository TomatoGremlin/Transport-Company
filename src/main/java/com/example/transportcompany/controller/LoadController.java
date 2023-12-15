package com.example.transportcompany.controller;

import com.example.transportcompany.dto.LoadDTO;
import com.example.transportcompany.service.LoadService;
import org.springframework.beans.factory.annotation.Autowired;
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
        loadService.updateLoadById(id, loadDTO);
        return ResponseEntity.ok("The Load has been edited");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteLoad(@PathVariable Long id){
        loadService.deleteLoadById(id);
        return ResponseEntity.ok("The Load has been deleted");
    }

}

