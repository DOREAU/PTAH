package org.aelb.application.ptah.service.impl;

import org.aelb.application.ptah.service.LienPageWebService;
import org.aelb.application.ptah.domain.LienPageWeb;
import org.aelb.application.ptah.repository.LienPageWebRepository;
import org.aelb.application.ptah.repository.search.LienPageWebSearchRepository;
import org.aelb.application.ptah.service.dto.LienPageWebDTO;
import org.aelb.application.ptah.service.mapper.LienPageWebMapper;
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
 * Service Implementation for managing LienPageWeb.
 */
@Service
@Transactional
public class LienPageWebServiceImpl implements LienPageWebService {

    private final Logger log = LoggerFactory.getLogger(LienPageWebServiceImpl.class);

    private final LienPageWebRepository lienPageWebRepository;

    private final LienPageWebMapper lienPageWebMapper;

    private final LienPageWebSearchRepository lienPageWebSearchRepository;

    public LienPageWebServiceImpl(LienPageWebRepository lienPageWebRepository, LienPageWebMapper lienPageWebMapper, LienPageWebSearchRepository lienPageWebSearchRepository) {
        this.lienPageWebRepository = lienPageWebRepository;
        this.lienPageWebMapper = lienPageWebMapper;
        this.lienPageWebSearchRepository = lienPageWebSearchRepository;
    }

    /**
     * Save a lienPageWeb.
     *
     * @param lienPageWebDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LienPageWebDTO save(LienPageWebDTO lienPageWebDTO) {
        log.debug("Request to save LienPageWeb : {}", lienPageWebDTO);

        LienPageWeb lienPageWeb = lienPageWebMapper.toEntity(lienPageWebDTO);
        lienPageWeb = lienPageWebRepository.save(lienPageWeb);
        LienPageWebDTO result = lienPageWebMapper.toDto(lienPageWeb);
        lienPageWebSearchRepository.save(lienPageWeb);
        return result;
    }

    /**
     * Get all the lienPageWebs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LienPageWebDTO> findAll() {
        log.debug("Request to get all LienPageWebs");
        return lienPageWebRepository.findAll().stream()
            .map(lienPageWebMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one lienPageWeb by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LienPageWebDTO> findOne(Long id) {
        log.debug("Request to get LienPageWeb : {}", id);
        return lienPageWebRepository.findById(id)
            .map(lienPageWebMapper::toDto);
    }

    /**
     * Delete the lienPageWeb by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LienPageWeb : {}", id);
        lienPageWebRepository.deleteById(id);
        lienPageWebSearchRepository.deleteById(id);
    }

    /**
     * Search for the lienPageWeb corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LienPageWebDTO> search(String query) {
        log.debug("Request to search LienPageWebs for query {}", query);
        return StreamSupport
            .stream(lienPageWebSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(lienPageWebMapper::toDto)
            .collect(Collectors.toList());
    }
}
