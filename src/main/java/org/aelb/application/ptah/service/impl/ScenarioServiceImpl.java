package org.aelb.application.ptah.service.impl;

import org.aelb.application.ptah.service.ScenarioService;
import org.aelb.application.ptah.domain.Scenario;
import org.aelb.application.ptah.repository.ScenarioRepository;
import org.aelb.application.ptah.repository.search.ScenarioSearchRepository;
import org.aelb.application.ptah.service.dto.ScenarioDTO;
import org.aelb.application.ptah.service.mapper.ScenarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Scenario.
 */
@Service
@Transactional
public class ScenarioServiceImpl implements ScenarioService {

    private final Logger log = LoggerFactory.getLogger(ScenarioServiceImpl.class);

    private final ScenarioRepository scenarioRepository;

    private final ScenarioMapper scenarioMapper;

    private final ScenarioSearchRepository scenarioSearchRepository;

    public ScenarioServiceImpl(ScenarioRepository scenarioRepository, ScenarioMapper scenarioMapper, ScenarioSearchRepository scenarioSearchRepository) {
        this.scenarioRepository = scenarioRepository;
        this.scenarioMapper = scenarioMapper;
        this.scenarioSearchRepository = scenarioSearchRepository;
    }

    /**
     * Save a scenario.
     *
     * @param scenarioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ScenarioDTO save(ScenarioDTO scenarioDTO) {
        log.debug("Request to save Scenario : {}", scenarioDTO);

        Scenario scenario = scenarioMapper.toEntity(scenarioDTO);
        scenario = scenarioRepository.save(scenario);
        ScenarioDTO result = scenarioMapper.toDto(scenario);
        scenarioSearchRepository.save(scenario);
        return result;
    }

    /**
     * Get all the scenarios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ScenarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Scenarios");
        return scenarioRepository.findAll(pageable)
            .map(scenarioMapper::toDto);
    }


    /**
     * Get one scenario by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ScenarioDTO> findOne(Long id) {
        log.debug("Request to get Scenario : {}", id);
        return scenarioRepository.findById(id)
            .map(scenarioMapper::toDto);
    }

    /**
     * Delete the scenario by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Scenario : {}", id);
        scenarioRepository.deleteById(id);
        scenarioSearchRepository.deleteById(id);
    }

    /**
     * Search for the scenario corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ScenarioDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Scenarios for query {}", query);
        return scenarioSearchRepository.search(queryStringQuery(query), pageable)
            .map(scenarioMapper::toDto);
    }
}
