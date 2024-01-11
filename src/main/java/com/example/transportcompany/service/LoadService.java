package com.example.transportcompany.service;

import com.example.transportcompany.dto.LoadDTO;
import com.example.transportcompany.model.Customer;
import com.example.transportcompany.model.Load;
import com.example.transportcompany.repository.LoadRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoadService {
    private final LoadRepository loadRepo;
    @Autowired
    public LoadService(LoadRepository loadRepo) {
        this.loadRepo = loadRepo;
    }
    /**
     * Saves a new load based on the provided LoadDTO.
     *
     * @param loadDTO The data transfer object containing load information.
     */
    public void saveLoad(LoadDTO loadDTO) {
        Load loadToSave = new Load();
        loadToSave.setWeight(loadDTO.getWeight());
        loadRepo.save(loadToSave);
    }
    /**
     * Updates an existing load's weight by ID using the provided LoadDTO.
     *
     * @param loadId     The ID of the load to update.
     * @param updatedLoad The data transfer object containing updated load information.
     * @throws EntityNotFoundException If the load with the specified ID is not found.
     */
    public void updateLoadById(long loadId, LoadDTO updatedLoad) {
        Load loadToUpdate = findLoadById(loadId);

        double newWeight = updatedLoad.getWeight();
        loadToUpdate.setWeight(newWeight);
        loadRepo.save(loadToUpdate);
    }
    /**
     * Deletes a load by ID.
     *
     * @param id The ID of the load to be deleted.
     * @throws EntityNotFoundException If the load with the specified ID is not found.
     */
    public void deleteLoadById(long id) {
            findLoadById(id);
            loadRepo.deleteById(id);
    }
    /**
     * Finds a load by its ID.
     *
     * @param id The ID of the load to retrieve.
     * @return The Load object corresponding to the given ID.
     * @throws EntityNotFoundException If the load with the specified ID is not found.
     */
    public Load findLoadById(long id) {
        return loadRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Load not found with id: " + id));
    }
    /**
     * Retrieves a list of all loads.
     *
     * @return A list containing all loads.
     */
    public List<Load> findAllLoad() {
        return loadRepo.findAll();
    }

    /**
     * Adds loads to a set based on a set of load IDs.
     *
     * @param loadIds A set of load IDs.
     * @return A set containing the corresponding loads based on the provided IDs.
     */
    public Set<Load> addLoadsToSet(Set<Long>loadIds ){
        return loadIds.stream()
                .map(loadId -> findLoadById(loadId))
                .collect(Collectors.toSet());
    }


}
