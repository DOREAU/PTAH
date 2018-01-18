package org.aelb.application.ptah.service.impl;

import org.aelb.application.ptah.service.PersonneService;
import org.aelb.application.ptah.domain.Personne;
import org.aelb.application.ptah.repository.PersonneRepository;
import org.aelb.application.ptah.repository.search.PersonneSearchRepository;
import org.aelb.application.ptah.service.dto.PersonneDTO;
import org.aelb.application.ptah.service.mapper.PersonneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Personne.
 */
@Service
@Transactional
public class PersonneServiceImpl implements PersonneService {

    private final Logger log = LoggerFactory.getLogger(PersonneServiceImpl.class);

    private final PersonneRepository personneRepository;

    private final PersonneMapper personneMapper;

    private final PersonneSearchRepository personneSearchRepository;

    public PersonneServiceImpl(PersonneRepository personneRepository, PersonneMapper personneMapper, PersonneSearchRepository personneSearchRepository) {
        this.personneRepository = personneRepository;
        this.personneMapper = personneMapper;
        this.personneSearchRepository = personneSearchRepository;
    }

    /**
     * Save a personne.
     *
     * @param personneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PersonneDTO save(PersonneDTO personneDTO) {
        log.debug("Request to save Personne : {}", personneDTO);
        Personne personne = personneMapper.toEntity(personneDTO);
        personne = personneRepository.save(personne);
        PersonneDTO result = personneMapper.toDto(personne);
        personneSearchRepository.save(personne);
        return result;
    }

    /**
     * Get all the personnes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonneDTO> findAll() {
        log.debug("Request to get all Personnes");
        return personneRepository.findAll().stream()
            .map(personneMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one personne by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PersonneDTO findOne(Long id) {
        log.debug("Request to get Personne : {}", id);
        Personne personne = personneRepository.findOne(id);
        return personneMapper.toDto(personne);
    }

    /**
     * Delete the personne by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Personne : {}", id);
        personneRepository.delete(id);
        personneSearchRepository.delete(id);
    }

    /**
     * Search for the personne corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonneDTO> search(String query) {
        log.debug("Request to search Personnes for query {}", query);
        return StreamSupport
            .stream(personneSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(personneMapper::toDto)
            .collect(Collectors.toList());
    }
}
