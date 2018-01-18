package org.aelb.application.ptah.service;

import org.aelb.application.ptah.service.dto.PersonneDTO;
import java.util.List;

/**
 * Service Interface for managing Personne.
 */
public interface PersonneService {

    /**
     * Save a personne.
     *
     * @param personneDTO the entity to save
     * @return the persisted entity
     */
    PersonneDTO save(PersonneDTO personneDTO);

    /**
     * Get all the personnes.
     *
     * @return the list of entities
     */
    List<PersonneDTO> findAll();

    /**
     * Get the "id" personne.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PersonneDTO findOne(Long id);

    /**
     * Delete the "id" personne.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the personne corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<PersonneDTO> search(String query);
}