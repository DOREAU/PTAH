package org.aelb.application.ptah.service;

import org.aelb.application.ptah.service.dto.EquipeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Equipe.
 */
public interface EquipeService {

    /**
     * Save a equipe.
     *
     * @param equipeDTO the entity to save
     * @return the persisted entity
     */
    EquipeDTO save(EquipeDTO equipeDTO);

    /**
     * Get all the equipes.
     *
     * @return the list of entities
     */
    List<EquipeDTO> findAll();


    /**
     * Get the "id" equipe.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EquipeDTO> findOne(Long id);

    /**
     * Delete the "id" equipe.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the equipe corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<EquipeDTO> search(String query);
}
