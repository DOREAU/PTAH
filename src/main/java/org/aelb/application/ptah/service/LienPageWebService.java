package org.aelb.application.ptah.service;

import org.aelb.application.ptah.service.dto.LienPageWebDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing LienPageWeb.
 */
public interface LienPageWebService {

    /**
     * Save a lienPageWeb.
     *
     * @param lienPageWebDTO the entity to save
     * @return the persisted entity
     */
    LienPageWebDTO save(LienPageWebDTO lienPageWebDTO);

    /**
     * Get all the lienPageWebs.
     *
     * @return the list of entities
     */
    List<LienPageWebDTO> findAll();


    /**
     * Get the "id" lienPageWeb.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LienPageWebDTO> findOne(Long id);

    /**
     * Delete the "id" lienPageWeb.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the lienPageWeb corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<LienPageWebDTO> search(String query);
}
