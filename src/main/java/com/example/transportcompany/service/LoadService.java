package com.example.transportcompany.service;

import com.example.transportcompany.dto.LoadDTO;
import com.example.transportcompany.model.Load;
import com.example.transportcompany.model.TransportCompany;
import com.example.transportcompany.repository.LoadRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoadService {
    @Autowired
    private LoadRepository loadRepo;
    public void saveLoad(LoadDTO loadDTO) {
        Load loadToSave = new Load();
        loadToSave.setWeight(loadDTO.getWeight());
        loadRepo.save(loadToSave);
    }
    public void updateLoadById(long loadId, LoadDTO updatedLoad) {
        Load loadToUpdate = loadRepo.findById(loadId)
                .orElseThrow(() -> new EntityNotFoundException("Load not found with id: " + loadId));

        double newWeight = updatedLoad.getWeight();
        loadToUpdate.setWeight(newWeight);
        loadRepo.save(loadToUpdate);
    }
    public void deleteLoadById(long id) {
        loadRepo.deleteById(id);
    }
    public Load findLoadById(long id) {
        return loadRepo.findById(id).orElse(null);
    }

    public List<Load> findAllLoad() {
        return loadRepo.findAll();
    }


}
