package org.aelb.application.ptah.service;

import org.aelb.application.ptah.service.dto.EtatCodeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing EtatCode.
 */
public interface EtatCodeService {

    /**
     * Save a etatCode.
     *
     * @param etatCodeDTO the entity to save
     * @return the persisted entity
     */
    EtatCodeDTO save(EtatCodeDTO etatCodeDTO);

    /**
     * Get all the etatCodes.
     *
     * @return the list of entities
     */
    List<EtatCodeDTO> findAll();


    /**
     * Get the "id" etatCode.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EtatCodeDTO> findOne(Long id);

    /**
     * Delete the "id" etatCode.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the etatCode corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<EtatCodeDTO> search(String query);
}
