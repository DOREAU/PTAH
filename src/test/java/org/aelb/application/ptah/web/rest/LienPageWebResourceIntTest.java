package org.aelb.application.ptah.web.rest;

import org.aelb.application.ptah.PtahApp;

import org.aelb.application.ptah.domain.LienPageWeb;
import org.aelb.application.ptah.repository.LienPageWebRepository;
import org.aelb.application.ptah.repository.search.LienPageWebSearchRepository;
import org.aelb.application.ptah.service.LienPageWebService;
import org.aelb.application.ptah.service.dto.LienPageWebDTO;
import org.aelb.application.ptah.service.mapper.LienPageWebMapper;
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
 * Test class for the LienPageWebResource REST controller.
 *
 * @see LienPageWebResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PtahApp.class)
public class LienPageWebResourceIntTest {

    private static final String DEFAULT_CODE_SAISI = "AAAAAAAAAA";
    private static final String UPDATED_CODE_SAISI = "BBBBBBBBBB";

    private static final String DEFAULT_URL_CIBLE = "AAAAAAAAAA";
    private static final String UPDATED_URL_CIBLE = "BBBBBBBBBB";

    @Autowired
    private LienPageWebRepository lienPageWebRepository;

    @Autowired
    private LienPageWebMapper lienPageWebMapper;
    
    @Autowired
    private LienPageWebService lienPageWebService;

    /**
     * This repository is mocked in the org.aelb.application.ptah.repository.search test package.
     *
     * @see org.aelb.application.ptah.repository.search.LienPageWebSearchRepositoryMockConfiguration
     */
    @Autowired
    private LienPageWebSearchRepository mockLienPageWebSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLienPageWebMockMvc;

    private LienPageWeb lienPageWeb;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LienPageWebResource lienPageWebResource = new LienPageWebResource(lienPageWebService);
        this.restLienPageWebMockMvc = MockMvcBuilders.standaloneSetup(lienPageWebResource)
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
    public static LienPageWeb createEntity(EntityManager em) {
        LienPageWeb lienPageWeb = new LienPageWeb()
            .codeSaisi(DEFAULT_CODE_SAISI)
            .urlCible(DEFAULT_URL_CIBLE);
        return lienPageWeb;
    }

    @Before
    public void initTest() {
        lienPageWeb = createEntity(em);
    }

    @Test
    @Transactional
    public void createLienPageWeb() throws Exception {
        int databaseSizeBeforeCreate = lienPageWebRepository.findAll().size();

        // Create the LienPageWeb
        LienPageWebDTO lienPageWebDTO = lienPageWebMapper.toDto(lienPageWeb);
        restLienPageWebMockMvc.perform(post("/api/lien-page-webs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lienPageWebDTO)))
            .andExpect(status().isCreated());

        // Validate the LienPageWeb in the database
        List<LienPageWeb> lienPageWebList = lienPageWebRepository.findAll();
        assertThat(lienPageWebList).hasSize(databaseSizeBeforeCreate + 1);
        LienPageWeb testLienPageWeb = lienPageWebList.get(lienPageWebList.size() - 1);
        assertThat(testLienPageWeb.getCodeSaisi()).isEqualTo(DEFAULT_CODE_SAISI);
        assertThat(testLienPageWeb.getUrlCible()).isEqualTo(DEFAULT_URL_CIBLE);

        // Validate the LienPageWeb in Elasticsearch
        verify(mockLienPageWebSearchRepository, times(1)).save(testLienPageWeb);
    }

    @Test
    @Transactional
    public void createLienPageWebWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lienPageWebRepository.findAll().size();

        // Create the LienPageWeb with an existing ID
        lienPageWeb.setId(1L);
        LienPageWebDTO lienPageWebDTO = lienPageWebMapper.toDto(lienPageWeb);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLienPageWebMockMvc.perform(post("/api/lien-page-webs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lienPageWebDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LienPageWeb in the database
        List<LienPageWeb> lienPageWebList = lienPageWebRepository.findAll();
        assertThat(lienPageWebList).hasSize(databaseSizeBeforeCreate);

        // Validate the LienPageWeb in Elasticsearch
        verify(mockLienPageWebSearchRepository, times(0)).save(lienPageWeb);
    }

    @Test
    @Transactional
    public void getAllLienPageWebs() throws Exception {
        // Initialize the database
        lienPageWebRepository.saveAndFlush(lienPageWeb);

        // Get all the lienPageWebList
        restLienPageWebMockMvc.perform(get("/api/lien-page-webs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lienPageWeb.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeSaisi").value(hasItem(DEFAULT_CODE_SAISI.toString())))
            .andExpect(jsonPath("$.[*].urlCible").value(hasItem(DEFAULT_URL_CIBLE.toString())));
    }
    
    @Test
    @Transactional
    public void getLienPageWeb() throws Exception {
        // Initialize the database
        lienPageWebRepository.saveAndFlush(lienPageWeb);

        // Get the lienPageWeb
        restLienPageWebMockMvc.perform(get("/api/lien-page-webs/{id}", lienPageWeb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lienPageWeb.getId().intValue()))
            .andExpect(jsonPath("$.codeSaisi").value(DEFAULT_CODE_SAISI.toString()))
            .andExpect(jsonPath("$.urlCible").value(DEFAULT_URL_CIBLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLienPageWeb() throws Exception {
        // Get the lienPageWeb
        restLienPageWebMockMvc.perform(get("/api/lien-page-webs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLienPageWeb() throws Exception {
        // Initialize the database
        lienPageWebRepository.saveAndFlush(lienPageWeb);

        int databaseSizeBeforeUpdate = lienPageWebRepository.findAll().size();

        // Update the lienPageWeb
        LienPageWeb updatedLienPageWeb = lienPageWebRepository.findById(lienPageWeb.getId()).get();
        // Disconnect from session so that the updates on updatedLienPageWeb are not directly saved in db
        em.detach(updatedLienPageWeb);
        updatedLienPageWeb
            .codeSaisi(UPDATED_CODE_SAISI)
            .urlCible(UPDATED_URL_CIBLE);
        LienPageWebDTO lienPageWebDTO = lienPageWebMapper.toDto(updatedLienPageWeb);

        restLienPageWebMockMvc.perform(put("/api/lien-page-webs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lienPageWebDTO)))
            .andExpect(status().isOk());

        // Validate the LienPageWeb in the database
        List<LienPageWeb> lienPageWebList = lienPageWebRepository.findAll();
        assertThat(lienPageWebList).hasSize(databaseSizeBeforeUpdate);
        LienPageWeb testLienPageWeb = lienPageWebList.get(lienPageWebList.size() - 1);
        assertThat(testLienPageWeb.getCodeSaisi()).isEqualTo(UPDATED_CODE_SAISI);
        assertThat(testLienPageWeb.getUrlCible()).isEqualTo(UPDATED_URL_CIBLE);

        // Validate the LienPageWeb in Elasticsearch
        verify(mockLienPageWebSearchRepository, times(1)).save(testLienPageWeb);
    }

    @Test
    @Transactional
    public void updateNonExistingLienPageWeb() throws Exception {
        int databaseSizeBeforeUpdate = lienPageWebRepository.findAll().size();

        // Create the LienPageWeb
        LienPageWebDTO lienPageWebDTO = lienPageWebMapper.toDto(lienPageWeb);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLienPageWebMockMvc.perform(put("/api/lien-page-webs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lienPageWebDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LienPageWeb in the database
        List<LienPageWeb> lienPageWebList = lienPageWebRepository.findAll();
        assertThat(lienPageWebList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LienPageWeb in Elasticsearch
        verify(mockLienPageWebSearchRepository, times(0)).save(lienPageWeb);
    }

    @Test
    @Transactional
    public void deleteLienPageWeb() throws Exception {
        // Initialize the database
        lienPageWebRepository.saveAndFlush(lienPageWeb);

        int databaseSizeBeforeDelete = lienPageWebRepository.findAll().size();

        // Get the lienPageWeb
        restLienPageWebMockMvc.perform(delete("/api/lien-page-webs/{id}", lienPageWeb.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LienPageWeb> lienPageWebList = lienPageWebRepository.findAll();
        assertThat(lienPageWebList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LienPageWeb in Elasticsearch
        verify(mockLienPageWebSearchRepository, times(1)).deleteById(lienPageWeb.getId());
    }

    @Test
    @Transactional
    public void searchLienPageWeb() throws Exception {
        // Initialize the database
        lienPageWebRepository.saveAndFlush(lienPageWeb);
        when(mockLienPageWebSearchRepository.search(queryStringQuery("id:" + lienPageWeb.getId())))
            .thenReturn(Collections.singletonList(lienPageWeb));
        // Search the lienPageWeb
        restLienPageWebMockMvc.perform(get("/api/_search/lien-page-webs?query=id:" + lienPageWeb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lienPageWeb.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeSaisi").value(hasItem(DEFAULT_CODE_SAISI.toString())))
            .andExpect(jsonPath("$.[*].urlCible").value(hasItem(DEFAULT_URL_CIBLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LienPageWeb.class);
        LienPageWeb lienPageWeb1 = new LienPageWeb();
        lienPageWeb1.setId(1L);
        LienPageWeb lienPageWeb2 = new LienPageWeb();
        lienPageWeb2.setId(lienPageWeb1.getId());
        assertThat(lienPageWeb1).isEqualTo(lienPageWeb2);
        lienPageWeb2.setId(2L);
        assertThat(lienPageWeb1).isNotEqualTo(lienPageWeb2);
        lienPageWeb1.setId(null);
        assertThat(lienPageWeb1).isNotEqualTo(lienPageWeb2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LienPageWebDTO.class);
        LienPageWebDTO lienPageWebDTO1 = new LienPageWebDTO();
        lienPageWebDTO1.setId(1L);
        LienPageWebDTO lienPageWebDTO2 = new LienPageWebDTO();
        assertThat(lienPageWebDTO1).isNotEqualTo(lienPageWebDTO2);
        lienPageWebDTO2.setId(lienPageWebDTO1.getId());
        assertThat(lienPageWebDTO1).isEqualTo(lienPageWebDTO2);
        lienPageWebDTO2.setId(2L);
        assertThat(lienPageWebDTO1).isNotEqualTo(lienPageWebDTO2);
        lienPageWebDTO1.setId(null);
        assertThat(lienPageWebDTO1).isNotEqualTo(lienPageWebDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lienPageWebMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lienPageWebMapper.fromId(null)).isNull();
    }
}
