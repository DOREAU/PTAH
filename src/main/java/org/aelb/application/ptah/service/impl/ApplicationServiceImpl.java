package org.aelb.application.ptah.service.impl;

import org.aelb.application.ptah.service.ApplicationService;
import org.aelb.application.ptah.domain.Application;
import org.aelb.application.ptah.repository.ApplicationRepository;
import org.aelb.application.ptah.repository.search.ApplicationSearchRepository;
import org.aelb.application.ptah.service.dto.ApplicationDTO;
import org.aelb.application.ptah.service.mapper.ApplicationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Application.
 */
@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final Logger log = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    private final ApplicationRepository applicationRepository;

    private final ApplicationMapper applicationMapper;

    private final ApplicationSearchRepository applicationSearchRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, ApplicationMapper applicationMapper, ApplicationSearchRepository applicationSearchRepository) {
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
        this.applicationSearchRepository = applicationSearchRepository;
    }

    /**
     * Save a application.
     *
     * @param applicationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ApplicationDTO save(ApplicationDTO applicationDTO) {
        log.debug("Request to save Application : {}", applicationDTO);
        Application application = applicationMapper.toEntity(applicationDTO);
        application = applicationRepository.save(application);
        ApplicationDTO result = applicationMapper.toDto(application);
        applicationSearchRepository.save(application);
        return result;
    }

    /**
     * Get all the applications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Applications");
        return applicationRepository.findAll(pageable)
            .map(applicationMapper::toDto);
    }

    /**
     * Get one application by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ApplicationDTO findOne(Long id) {
        log.debug("Request to get Application : {}", id);
        Application application = applicationRepository.findOne(id);
        return applicationMapper.toDto(application);
    }

    /**
     * Delete the application by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Application : {}", id);
        applicationRepository.delete(id);
        applicationSearchRepository.delete(id);
    }

    /**
     * Search for the application corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Applications for query {}", query);
        Page<Application> result = applicationSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(applicationMapper::toDto);
    }
}
