package org.aelb.application.ptah.web.rest;

import org.aelb.application.ptah.PtahApp;

import org.aelb.application.ptah.domain.EtatCode;
import org.aelb.application.ptah.repository.EtatCodeRepository;
import org.aelb.application.ptah.repository.search.EtatCodeSearchRepository;
import org.aelb.application.ptah.service.EtatCodeService;
import org.aelb.application.ptah.service.dto.EtatCodeDTO;
import org.aelb.application.ptah.service.mapper.EtatCodeMapper;
import org.aelb.application.ptah.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static org.aelb.application.ptah.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EtatCodeResource REST controller.
 *
 * @see EtatCodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PtahApp.class)
public class EtatCodeResourceIntTest {

    private static final Integer DEFAULT_KEY = 1;
    private static final Integer UPDATED_KEY = 2;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private EtatCodeRepository etatCodeRepository;

    @Autowired
    private EtatCodeMapper etatCodeMapper;
    
    @Autowired
    private EtatCodeService etatCodeService;

    /**
     * This repository is mocked in the org.aelb.application.ptah.repository.search test package.
     *
     * @see org.aelb.application.ptah.repository.search.EtatCodeSearchRepositoryMockConfiguration
     */
    @Autowired
    private EtatCodeSearchRepository mockEtatCodeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEtatCodeMockMvc;

    private EtatCode etatCode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtatCodeResource etatCodeResource = new EtatCodeResource(etatCodeService);
        this.restEtatCodeMockMvc = MockMvcBuilders.standaloneSetup(etatCodeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtatCode createEntity(EntityManager em) {
        EtatCode etatCode = new EtatCode()
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return etatCode;
    }

    @Before
    public void initTest() {
        etatCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtatCode() throws Exception {
        int databaseSizeBeforeCreate = etatCodeRepository.findAll().size();

        // Create the EtatCode
        EtatCodeDTO etatCodeDTO = etatCodeMapper.toDto(etatCode);
        restEtatCodeMockMvc.perform(post("/api/etat-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etatCodeDTO)))
            .andExpect(status().isCreated());

        // Validate the EtatCode in the database
        List<EtatCode> etatCodeList = etatCodeRepository.findAll();
        assertThat(etatCodeList).hasSize(databaseSizeBeforeCreate + 1);
        EtatCode testEtatCode = etatCodeList.get(etatCodeList.size() - 1);
        assertThat(testEtatCode.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testEtatCode.getValue()).isEqualTo(DEFAULT_VALUE);

        // Validate the EtatCode in Elasticsearch
        verify(mockEtatCodeSearchRepository, times(1)).save(testEtatCode);
    }

    @Test
    @Transactional
    public void createEtatCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etatCodeRepository.findAll().size();

        // Create the EtatCode with an existing ID
        etatCode.setId(1L);
        EtatCodeDTO etatCodeDTO = etatCodeMapper.toDto(etatCode);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtatCodeMockMvc.perform(post("/api/etat-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etatCodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EtatCode in the database
        List<EtatCode> etatCodeList = etatCodeRepository.findAll();
        assertThat(etatCodeList).hasSize(databaseSizeBeforeCreate);

        // Validate the EtatCode in Elasticsearch
        verify(mockEtatCodeSearchRepository, times(0)).save(etatCode);
    }

    @Test
    @Transactional
    public void getAllEtatCodes() throws Exception {
        // Initialize the database
        etatCodeRepository.saveAndFlush(etatCode);

        // Get all the etatCodeList
        restEtatCodeMockMvc.perform(get("/api/etat-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etatCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getEtatCode() throws Exception {
        // Initialize the database
        etatCodeRepository.saveAndFlush(etatCode);

        // Get the etatCode
        restEtatCodeMockMvc.perform(get("/api/etat-codes/{id}", etatCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(etatCode.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEtatCode() throws Exception {
        // Get the etatCode
        restEtatCodeMockMvc.perform(get("/api/etat-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtatCode() throws Exception {
        // Initialize the database
        etatCodeRepository.saveAndFlush(etatCode);

        int databaseSizeBeforeUpdate = etatCodeRepository.findAll().size();

        // Update the etatCode
        EtatCode updatedEtatCode = etatCodeRepository.findById(etatCode.getId()).get();
        // Disconnect from session so that the updates on updatedEtatCode are not directly saved in db
        em.detach(updatedEtatCode);
        updatedEtatCode
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);
        EtatCodeDTO etatCodeDTO = etatCodeMapper.toDto(updatedEtatCode);

        restEtatCodeMockMvc.perform(put("/api/etat-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etatCodeDTO)))
            .andExpect(status().isOk());

        // Validate the EtatCode in the database
        List<EtatCode> etatCodeList = etatCodeRepository.findAll();
        assertThat(etatCodeList).hasSize(databaseSizeBeforeUpdate);
        EtatCode testEtatCode = etatCodeList.get(etatCodeList.size() - 1);
        assertThat(testEtatCode.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testEtatCode.getValue()).isEqualTo(UPDATED_VALUE);

        // Validate the EtatCode in Elasticsearch
        verify(mockEtatCodeSearchRepository, times(1)).save(testEtatCode);
    }

    @Test
    @Transactional
    public void updateNonExistingEtatCode() throws Exception {
        int databaseSizeBeforeUpdate = etatCodeRepository.findAll().size();

        // Create the EtatCode
        EtatCodeDTO etatCodeDTO = etatCodeMapper.toDto(etatCode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtatCodeMockMvc.perform(put("/api/etat-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etatCodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EtatCode in the database
        List<EtatCode> etatCodeList = etatCodeRepository.findAll();
        assertThat(etatCodeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EtatCode in Elasticsearch
        verify(mockEtatCodeSearchRepository, times(0)).save(etatCode);
    }

    @Test
    @Transactional
    public void deleteEtatCode() throws Exception {
        // Initialize the database
        etatCodeRepository.saveAndFlush(etatCode);

        int databaseSizeBeforeDelete = etatCodeRepository.findAll().size();

        // Get the etatCode
        restEtatCodeMockMvc.perform(delete("/api/etat-codes/{id}", etatCode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EtatCode> etatCodeList = etatCodeRepository.findAll();
        assertThat(etatCodeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EtatCode in Elasticsearch
        verify(mockEtatCodeSearchRepository, times(1)).deleteById(etatCode.getId());
    }

    @Test
    @Transactional
    public void searchEtatCode() throws Exception {
        // Initialize the database
        etatCodeRepository.saveAndFlush(etatCode);
        when(mockEtatCodeSearchRepository.search(queryStringQuery("id:" + etatCode.getId())))
            .thenReturn(Collections.singletonList(etatCode));
        // Search the etatCode
        restEtatCodeMockMvc.perform(get("/api/_search/etat-codes?query=id:" + etatCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etatCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtatCode.class);
        EtatCode etatCode1 = new EtatCode();
        etatCode1.setId(1L);
        EtatCode etatCode2 = new EtatCode();
        etatCode2.setId(etatCode1.getId());
        assertThat(etatCode1).isEqualTo(etatCode2);
        etatCode2.setId(2L);
        assertThat(etatCode1).isNotEqualTo(etatCode2);
        etatCode1.setId(null);
        assertThat(etatCode1).isNotEqualTo(etatCode2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtatCodeDTO.class);
        EtatCodeDTO etatCodeDTO1 = new EtatCodeDTO();
        etatCodeDTO1.setId(1L);
        EtatCodeDTO etatCodeDTO2 = new EtatCodeDTO();
        assertThat(etatCodeDTO1).isNotEqualTo(etatCodeDTO2);
        etatCodeDTO2.setId(etatCodeDTO1.getId());
        assertThat(etatCodeDTO1).isEqualTo(etatCodeDTO2);
        etatCodeDTO2.setId(2L);
        assertThat(etatCodeDTO1).isNotEqualTo(etatCodeDTO2);
        etatCodeDTO1.setId(null);
        assertThat(etatCodeDTO1).isNotEqualTo(etatCodeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(etatCodeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(etatCodeMapper.fromId(null)).isNull();
    }
}
