package org.aelb.application.ptah.service.impl;

import org.aelb.application.ptah.service.CodeAccesService;
import org.aelb.application.ptah.domain.CodeAcces;
import org.aelb.application.ptah.repository.CodeAccesRepository;
import org.aelb.application.ptah.repository.search.CodeAccesSearchRepository;
import org.aelb.application.ptah.service.dto.CodeAccesDTO;
import org.aelb.application.ptah.service.mapper.CodeAccesMapper;
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
 * Service Implementation for managing CodeAcces.
 */
@Service
@Transactional
public class CodeAccesServiceImpl implements CodeAccesService {

    private final Logger log = LoggerFactory.getLogger(CodeAccesServiceImpl.class);

    private final CodeAccesRepository codeAccesRepository;

    private final CodeAccesMapper codeAccesMapper;

    private final CodeAccesSearchRepository codeAccesSearchRepository;

    public CodeAccesServiceImpl(CodeAccesRepository codeAccesRepository, CodeAccesMapper codeAccesMapper, CodeAccesSearchRepository codeAccesSearchRepository) {
        this.codeAccesRepository = codeAccesRepository;
        this.codeAccesMapper = codeAccesMapper;
        this.codeAccesSearchRepository = codeAccesSearchRepository;
    }

    /**
     * Save a codeAcces.
     *
     * @param codeAccesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CodeAccesDTO save(CodeAccesDTO codeAccesDTO) {
        log.debug("Request to save CodeAcces : {}", codeAccesDTO);

        CodeAcces codeAcces = codeAccesMapper.toEntity(codeAccesDTO);
        codeAcces = codeAccesRepository.save(codeAcces);
        CodeAccesDTO result = codeAccesMapper.toDto(codeAcces);
        codeAccesSearchRepository.save(codeAcces);
        return result;
    }

    /**
     * Get all the codeAcces.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CodeAccesDTO> findAll() {
        log.debug("Request to get all CodeAcces");
        return codeAccesRepository.findAll().stream()
            .map(codeAccesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one codeAcces by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CodeAccesDTO> findOne(Long id) {
        log.debug("Request to get CodeAcces : {}", id);
        return codeAccesRepository.findById(id)
            .map(codeAccesMapper::toDto);
    }

    /**
     * Delete the codeAcces by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CodeAcces : {}", id);
        codeAccesRepository.deleteById(id);
        codeAccesSearchRepository.deleteById(id);
    }

    /**
     * Search for the codeAcces corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CodeAccesDTO> search(String query) {
        log.debug("Request to search CodeAcces for query {}", query);
        return StreamSupport
            .stream(codeAccesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(codeAccesMapper::toDto)
            .collect(Collectors.toList());
    }
}
