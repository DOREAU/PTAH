package org.aelb.application.ptah.service;

import org.aelb.application.ptah.service.dto.ApplicationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Application.
 */
public interface ApplicationService {

    /**
     * Save a application.
     *
     * @param applicationDTO the entity to save
     * @return the persisted entity
     */
    ApplicationDTO save(ApplicationDTO applicationDTO);

    /**
     * Get all the applications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ApplicationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" application.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ApplicationDTO findOne(Long id);

    /**
     * Delete the "id" application.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the application corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ApplicationDTO> search(String query, Pageable pageable);
}
