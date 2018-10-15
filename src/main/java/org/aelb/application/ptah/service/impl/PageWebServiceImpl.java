package org.aelb.application.ptah.service.impl;

import org.aelb.application.ptah.service.PageWebService;
import org.aelb.application.ptah.domain.PageWeb;
import org.aelb.application.ptah.repository.PageWebRepository;
import org.aelb.application.ptah.repository.search.PageWebSearchRepository;
import org.aelb.application.ptah.service.dto.PageWebDTO;
import org.aelb.application.ptah.service.mapper.PageWebMapper;
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
 * Service Implementation for managing PageWeb.
 */
@Service
@Transactional
public class PageWebServiceImpl implements PageWebService {

    private final Logger log = LoggerFactory.getLogger(PageWebServiceImpl.class);

    private final PageWebRepository pageWebRepository;

    private final PageWebMapper pageWebMapper;

    private final PageWebSearchRepository pageWebSearchRepository;

    public PageWebServiceImpl(PageWebRepository pageWebRepository, PageWebMapper pageWebMapper, PageWebSearchRepository pageWebSearchRepository) {
        this.pageWebRepository = pageWebRepository;
        this.pageWebMapper = pageWebMapper;
        this.pageWebSearchRepository = pageWebSearchRepository;
    }

    /**
     * Save a pageWeb.
     *
     * @param pageWebDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PageWebDTO save(PageWebDTO pageWebDTO) {
        log.debug("Request to save PageWeb : {}", pageWebDTO);

        PageWeb pageWeb = pageWebMapper.toEntity(pageWebDTO);
        pageWeb = pageWebRepository.save(pageWeb);
        PageWebDTO result = pageWebMapper.toDto(pageWeb);
        pageWebSearchRepository.save(pageWeb);
        return result;
    }

    /**
     * Get all the pageWebs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PageWebDTO> findAll() {
        log.debug("Request to get all PageWebs");
        return pageWebRepository.findAll().stream()
            .map(pageWebMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pageWeb by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PageWebDTO> findOne(Long id) {
        log.debug("Request to get PageWeb : {}", id);
        return pageWebRepository.findById(id)
            .map(pageWebMapper::toDto);
    }

    /**
     * Delete the pageWeb by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PageWeb : {}", id);
        pageWebRepository.deleteById(id);
        pageWebSearchRepository.deleteById(id);
    }

    /**
     * Search for the pageWeb corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PageWebDTO> search(String query) {
        log.debug("Request to search PageWebs for query {}", query);
        return StreamSupport
            .stream(pageWebSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(pageWebMapper::toDto)
            .collect(Collectors.toList());
    }
}
