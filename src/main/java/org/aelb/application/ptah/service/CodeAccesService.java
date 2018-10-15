package org.aelb.application.ptah.service;

import org.aelb.application.ptah.service.dto.CodeAccesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CodeAcces.
 */
public interface CodeAccesService {

    /**
     * Save a codeAcces.
     *
     * @param codeAccesDTO the entity to save
     * @return the persisted entity
     */
    CodeAccesDTO save(CodeAccesDTO codeAccesDTO);

    /**
     * Get all the codeAcces.
     *
     * @return the list of entities
     */
    List<CodeAccesDTO> findAll();


    /**
     * Get the "id" codeAcces.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CodeAccesDTO> findOne(Long id);

    /**
     * Delete the "id" codeAcces.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the codeAcces corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<CodeAccesDTO> search(String query);
}
