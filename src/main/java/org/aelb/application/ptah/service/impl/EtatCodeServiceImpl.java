package org.aelb.application.ptah.service.impl;

import org.aelb.application.ptah.service.EtatCodeService;
import org.aelb.application.ptah.domain.EtatCode;
import org.aelb.application.ptah.repository.EtatCodeRepository;
import org.aelb.application.ptah.repository.search.EtatCodeSearchRepository;
import org.aelb.application.ptah.service.dto.EtatCodeDTO;
import org.aelb.application.ptah.service.mapper.EtatCodeMapper;
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
 * Service Implementation for managing EtatCode.
 */
@Service
@Transactional
public class EtatCodeServiceImpl implements EtatCodeService {

    private final Logger log = LoggerFactory.getLogger(EtatCodeServiceImpl.class);

    private final EtatCodeRepository etatCodeRepository;

    private final EtatCodeMapper etatCodeMapper;

    private final EtatCodeSearchRepository etatCodeSearchRepository;

    public EtatCodeServiceImpl(EtatCodeRepository etatCodeRepository, EtatCodeMapper etatCodeMapper, EtatCodeSearchRepository etatCodeSearchRepository) {
        this.etatCodeRepository = etatCodeRepository;
        this.etatCodeMapper = etatCodeMapper;
        this.etatCodeSearchRepository = etatCodeSearchRepository;
    }

    /**
     * Save a etatCode.
     *
     * @param etatCodeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EtatCodeDTO save(EtatCodeDTO etatCodeDTO) {
        log.debug("Request to save EtatCode : {}", etatCodeDTO);

        EtatCode etatCode = etatCodeMapper.toEntity(etatCodeDTO);
        etatCode = etatCodeRepository.save(etatCode);
        EtatCodeDTO result = etatCodeMapper.toDto(etatCode);
        etatCodeSearchRepository.save(etatCode);
        return result;
    }

    /**
     * Get all the etatCodes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EtatCodeDTO> findAll() {
        log.debug("Request to get all EtatCodes");
        return etatCodeRepository.findAll().stream()
            .map(etatCodeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one etatCode by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EtatCodeDTO> findOne(Long id) {
        log.debug("Request to get EtatCode : {}", id);
        return etatCodeRepository.findById(id)
            .map(etatCodeMapper::toDto);
    }

    /**
     * Delete the etatCode by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EtatCode : {}", id);
        etatCodeRepository.deleteById(id);
        etatCodeSearchRepository.deleteById(id);
    }

    /**
     * Search for the etatCode corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EtatCodeDTO> search(String query) {
        log.debug("Request to search EtatCodes for query {}", query);
        return StreamSupport
            .stream(etatCodeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(etatCodeMapper::toDto)
            .collect(Collectors.toList());
    }
}
