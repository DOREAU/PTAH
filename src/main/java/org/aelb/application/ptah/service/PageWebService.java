package org.aelb.application.ptah.service;

import org.aelb.application.ptah.service.dto.PageWebDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PageWeb.
 */
public interface PageWebService {

    /**
     * Save a pageWeb.
     *
     * @param pageWebDTO the entity to save
     * @return the persisted entity
     */
    PageWebDTO save(PageWebDTO pageWebDTO);

    /**
     * Get all the pageWebs.
     *
     * @return the list of entities
     */
    List<PageWebDTO> findAll();


    /**
     * Get the "id" pageWeb.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PageWebDTO> findOne(Long id);

    /**
     * Delete the "id" pageWeb.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pageWeb corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<PageWebDTO> search(String query);
}
