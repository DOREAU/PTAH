package org.aelb.application.ptah.web.rest;

import org.aelb.application.ptah.PtahApp;

import org.aelb.application.ptah.domain.CodeAcces;
import org.aelb.application.ptah.repository.CodeAccesRepository;
import org.aelb.application.ptah.repository.search.CodeAccesSearchRepository;
import org.aelb.application.ptah.service.CodeAccesService;
import org.aelb.application.ptah.service.dto.CodeAccesDTO;
import org.aelb.application.ptah.service.mapper.CodeAccesMapper;
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
 * Test class for the CodeAccesResource REST controller.
 *
 * @see CodeAccesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PtahApp.class)
public class CodeAccesResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private CodeAccesRepository codeAccesRepository;

    @Autowired
    private CodeAccesMapper codeAccesMapper;
    
    @Autowired
    private CodeAccesService codeAccesService;

    /**
     * This repository is mocked in the org.aelb.application.ptah.repository.search test package.
     *
     * @see org.aelb.application.ptah.repository.search.CodeAccesSearchRepositoryMockConfiguration
     */
    @Autowired
    private CodeAccesSearchRepository mockCodeAccesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCodeAccesMockMvc;

    private CodeAcces codeAcces;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CodeAccesResource codeAccesResource = new CodeAccesResource(codeAccesService);
        this.restCodeAccesMockMvc = MockMvcBuilders.standaloneSetup(codeAccesResource)
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
    public static CodeAcces createEntity(EntityManager em) {
        CodeAcces codeAcces = new CodeAcces()
            .code(DEFAULT_CODE);
        return codeAcces;
    }

    @Before
    public void initTest() {
        codeAcces = createEntity(em);
    }

    @Test
    @Transactional
    public void createCodeAcces() throws Exception {
        int databaseSizeBeforeCreate = codeAccesRepository.findAll().size();

        // Create the CodeAcces
        CodeAccesDTO codeAccesDTO = codeAccesMapper.toDto(codeAcces);
        restCodeAccesMockMvc.perform(post("/api/code-acces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codeAccesDTO)))
            .andExpect(status().isCreated());

        // Validate the CodeAcces in the database
        List<CodeAcces> codeAccesList = codeAccesRepository.findAll();
        assertThat(codeAccesList).hasSize(databaseSizeBeforeCreate + 1);
        CodeAcces testCodeAcces = codeAccesList.get(codeAccesList.size() - 1);
        assertThat(testCodeAcces.getCode()).isEqualTo(DEFAULT_CODE);

        // Validate the CodeAcces in Elasticsearch
        verify(mockCodeAccesSearchRepository, times(1)).save(testCodeAcces);
    }

    @Test
    @Transactional
    public void createCodeAccesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = codeAccesRepository.findAll().size();

        // Create the CodeAcces with an existing ID
        codeAcces.setId(1L);
        CodeAccesDTO codeAccesDTO = codeAccesMapper.toDto(codeAcces);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodeAccesMockMvc.perform(post("/api/code-acces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codeAccesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CodeAcces in the database
        List<CodeAcces> codeAccesList = codeAccesRepository.findAll();
        assertThat(codeAccesList).hasSize(databaseSizeBeforeCreate);

        // Validate the CodeAcces in Elasticsearch
        verify(mockCodeAccesSearchRepository, times(0)).save(codeAcces);
    }

    @Test
    @Transactional
    public void getAllCodeAcces() throws Exception {
        // Initialize the database
        codeAccesRepository.saveAndFlush(codeAcces);

        // Get all the codeAccesList
        restCodeAccesMockMvc.perform(get("/api/code-acces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codeAcces.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getCodeAcces() throws Exception {
        // Initialize the database
        codeAccesRepository.saveAndFlush(codeAcces);

        // Get the codeAcces
        restCodeAccesMockMvc.perform(get("/api/code-acces/{id}", codeAcces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(codeAcces.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCodeAcces() throws Exception {
        // Get the codeAcces
        restCodeAccesMockMvc.perform(get("/api/code-acces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCodeAcces() throws Exception {
        // Initialize the database
        codeAccesRepository.saveAndFlush(codeAcces);

        int databaseSizeBeforeUpdate = codeAccesRepository.findAll().size();

        // Update the codeAcces
        CodeAcces updatedCodeAcces = codeAccesRepository.findById(codeAcces.getId()).get();
        // Disconnect from session so that the updates on updatedCodeAcces are not directly saved in db
        em.detach(updatedCodeAcces);
        updatedCodeAcces
            .code(UPDATED_CODE);
        CodeAccesDTO codeAccesDTO = codeAccesMapper.toDto(updatedCodeAcces);

        restCodeAccesMockMvc.perform(put("/api/code-acces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codeAccesDTO)))
            .andExpect(status().isOk());

        // Validate the CodeAcces in the database
        List<CodeAcces> codeAccesList = codeAccesRepository.findAll();
        assertThat(codeAccesList).hasSize(databaseSizeBeforeUpdate);
        CodeAcces testCodeAcces = codeAccesList.get(codeAccesList.size() - 1);
        assertThat(testCodeAcces.getCode()).isEqualTo(UPDATED_CODE);

        // Validate the CodeAcces in Elasticsearch
        verify(mockCodeAccesSearchRepository, times(1)).save(testCodeAcces);
    }

    @Test
    @Transactional
    public void updateNonExistingCodeAcces() throws Exception {
        int databaseSizeBeforeUpdate = codeAccesRepository.findAll().size();

        // Create the CodeAcces
        CodeAccesDTO codeAccesDTO = codeAccesMapper.toDto(codeAcces);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodeAccesMockMvc.perform(put("/api/code-acces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codeAccesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CodeAcces in the database
        List<CodeAcces> codeAccesList = codeAccesRepository.findAll();
        assertThat(codeAccesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CodeAcces in Elasticsearch
        verify(mockCodeAccesSearchRepository, times(0)).save(codeAcces);
    }

    @Test
    @Transactional
    public void deleteCodeAcces() throws Exception {
        // Initialize the database
        codeAccesRepository.saveAndFlush(codeAcces);

        int databaseSizeBeforeDelete = codeAccesRepository.findAll().size();

        // Get the codeAcces
        restCodeAccesMockMvc.perform(delete("/api/code-acces/{id}", codeAcces.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CodeAcces> codeAccesList = codeAccesRepository.findAll();
        assertThat(codeAccesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CodeAcces in Elasticsearch
        verify(mockCodeAccesSearchRepository, times(1)).deleteById(codeAcces.getId());
    }

    @Test
    @Transactional
    public void searchCodeAcces() throws Exception {
        // Initialize the database
        codeAccesRepository.saveAndFlush(codeAcces);
        when(mockCodeAccesSearchRepository.search(queryStringQuery("id:" + codeAcces.getId())))
            .thenReturn(Collections.singletonList(codeAcces));
        // Search the codeAcces
        restCodeAccesMockMvc.perform(get("/api/_search/code-acces?query=id:" + codeAcces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codeAcces.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodeAcces.class);
        CodeAcces codeAcces1 = new CodeAcces();
        codeAcces1.setId(1L);
        CodeAcces codeAcces2 = new CodeAcces();
        codeAcces2.setId(codeAcces1.getId());
        assertThat(codeAcces1).isEqualTo(codeAcces2);
        codeAcces2.setId(2L);
        assertThat(codeAcces1).isNotEqualTo(codeAcces2);
        codeAcces1.setId(null);
        assertThat(codeAcces1).isNotEqualTo(codeAcces2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodeAccesDTO.class);
        CodeAccesDTO codeAccesDTO1 = new CodeAccesDTO();
        codeAccesDTO1.setId(1L);
        CodeAccesDTO codeAccesDTO2 = new CodeAccesDTO();
        assertThat(codeAccesDTO1).isNotEqualTo(codeAccesDTO2);
        codeAccesDTO2.setId(codeAccesDTO1.getId());
        assertThat(codeAccesDTO1).isEqualTo(codeAccesDTO2);
        codeAccesDTO2.setId(2L);
        assertThat(codeAccesDTO1).isNotEqualTo(codeAccesDTO2);
        codeAccesDTO1.setId(null);
        assertThat(codeAccesDTO1).isNotEqualTo(codeAccesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(codeAccesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(codeAccesMapper.fromId(null)).isNull();
    }
}
