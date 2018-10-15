package org.aelb.application.ptah.service.impl;

import org.aelb.application.ptah.service.EquipeService;
import org.aelb.application.ptah.domain.Equipe;
import org.aelb.application.ptah.repository.EquipeRepository;
import org.aelb.application.ptah.repository.search.EquipeSearchRepository;
import org.aelb.application.ptah.service.dto.EquipeDTO;
import org.aelb.application.ptah.service.mapper.EquipeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Equipe.
 */
@Service
@Transactional
public class EquipeServiceImpl implements EquipeService {

    private final Logger log = LoggerFactory.getLogger(EquipeServiceImpl.class);

    private final EquipeRepository equipeRepository;

    private final EquipeMapper equipeMapper;

    private final EquipeSearchRepository equipeSearchRepository;

    public EquipeServiceImpl(EquipeRepository equipeRepository, EquipeMapper equipeMapper, EquipeSearchRepository equipeSearchRepository) {
        this.equipeRepository = equipeRepository;
        this.equipeMapper = equipeMapper;
        this.equipeSearchRepository = equipeSearchRepository;
    }

    /**
     * Save a equipe.
     *
     * @param equipeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EquipeDTO save(EquipeDTO equipeDTO) {
        log.debug("Request to save Equipe : {}", equipeDTO);

        Equipe equipe = equipeMapper.toEntity(equipeDTO);
        equipe = equipeRepository.save(equipe);
        EquipeDTO result = equipeMapper.toDto(equipe);
        equipeSearchRepository.save(equipe);
        return result;
    }

    /**
     * Get all the equipes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EquipeDTO> findAll() {
        log.debug("Request to get all Equipes");
        return equipeRepository.findAll().stream()
            .map(equipeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one equipe by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EquipeDTO> findOne(Long id) {
        log.debug("Request to get Equipe : {}", id);
        return equipeRepository.findById(id)
            .map(equipeMapper::toDto);
    }

    /**
     * Delete the equipe by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Equipe : {}", id);
        equipeRepository.deleteById(id);
        equipeSearchRepository.deleteById(id);
    }

    /**
     * Search for the equipe corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EquipeDTO> search(String query) {
        log.debug("Request to search Equipes for query {}", query);
        return StreamSupport
            .stream(equipeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(equipeMapper::toDto)
            .collect(Collectors.toList());
    }
}
