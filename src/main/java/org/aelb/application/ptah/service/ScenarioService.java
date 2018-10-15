package org.aelb.application.ptah.service;

import org.aelb.application.ptah.service.dto.ScenarioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Scenario.
 */
public interface ScenarioService {

    /**
     * Save a scenario.
     *
     * @param scenarioDTO the entity to save
     * @return the persisted entity
     */
    ScenarioDTO save(ScenarioDTO scenarioDTO);

    /**
     * Get all the scenarios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ScenarioDTO> findAll(Pageable pageable);


    /**
     * Get the "id" scenario.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ScenarioDTO> findOne(Long id);

    /**
     * Delete the "id" scenario.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the scenario corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ScenarioDTO> search(String query, Pageable pageable);
}
