package org.aelb.application.ptah.web.rest;

import org.aelb.application.ptah.PtahApp;

import org.aelb.application.ptah.domain.Scenario;
import org.aelb.application.ptah.repository.ScenarioRepository;
import org.aelb.application.ptah.repository.search.ScenarioSearchRepository;
import org.aelb.application.ptah.service.ScenarioService;
import org.aelb.application.ptah.service.dto.ScenarioDTO;
import org.aelb.application.ptah.service.mapper.ScenarioMapper;
import org.aelb.application.ptah.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
 * Test class for the ScenarioResource REST controller.
 *
 * @see ScenarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PtahApp.class)
public class ScenarioResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private ScenarioRepository scenarioRepository;

    @Autowired
    private ScenarioMapper scenarioMapper;
    
    @Autowired
    private ScenarioService scenarioService;

    /**
     * This repository is mocked in the org.aelb.application.ptah.repository.search test package.
     *
     * @see org.aelb.application.ptah.repository.search.ScenarioSearchRepositoryMockConfiguration
     */
    @Autowired
    private ScenarioSearchRepository mockScenarioSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restScenarioMockMvc;

    private Scenario scenario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ScenarioResource scenarioResource = new ScenarioResource(scenarioService);
        this.restScenarioMockMvc = MockMvcBuilders.standaloneSetup(scenarioResource)
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
    public static Scenario createEntity(EntityManager em) {
        Scenario scenario = new Scenario()
            .nom(DEFAULT_NOM);
        return scenario;
    }

    @Before
    public void initTest() {
        scenario = createEntity(em);
    }

    @Test
    @Transactional
    public void createScenario() throws Exception {
        int databaseSizeBeforeCreate = scenarioRepository.findAll().size();

        // Create the Scenario
        ScenarioDTO scenarioDTO = scenarioMapper.toDto(scenario);
        restScenarioMockMvc.perform(post("/api/scenarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scenarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Scenario in the database
        List<Scenario> scenarioList = scenarioRepository.findAll();
        assertThat(scenarioList).hasSize(databaseSizeBeforeCreate + 1);
        Scenario testScenario = scenarioList.get(scenarioList.size() - 1);
        assertThat(testScenario.getNom()).isEqualTo(DEFAULT_NOM);

        // Validate the Scenario in Elasticsearch
        verify(mockScenarioSearchRepository, times(1)).save(testScenario);
    }

    @Test
    @Transactional
    public void createScenarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scenarioRepository.findAll().size();

        // Create the Scenario with an existing ID
        scenario.setId(1L);
        ScenarioDTO scenarioDTO = scenarioMapper.toDto(scenario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScenarioMockMvc.perform(post("/api/scenarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scenarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scenario in the database
        List<Scenario> scenarioList = scenarioRepository.findAll();
        assertThat(scenarioList).hasSize(databaseSizeBeforeCreate);

        // Validate the Scenario in Elasticsearch
        verify(mockScenarioSearchRepository, times(0)).save(scenario);
    }

    @Test
    @Transactional
    public void getAllScenarios() throws Exception {
        // Initialize the database
        scenarioRepository.saveAndFlush(scenario);

        // Get all the scenarioList
        restScenarioMockMvc.perform(get("/api/scenarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scenario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }
    
    @Test
    @Transactional
    public void getScenario() throws Exception {
        // Initialize the database
        scenarioRepository.saveAndFlush(scenario);

        // Get the scenario
        restScenarioMockMvc.perform(get("/api/scenarios/{id}", scenario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(scenario.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingScenario() throws Exception {
        // Get the scenario
        restScenarioMockMvc.perform(get("/api/scenarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScenario() throws Exception {
        // Initialize the database
        scenarioRepository.saveAndFlush(scenario);

        int databaseSizeBeforeUpdate = scenarioRepository.findAll().size();

        // Update the scenario
        Scenario updatedScenario = scenarioRepository.findById(scenario.getId()).get();
        // Disconnect from session so that the updates on updatedScenario are not directly saved in db
        em.detach(updatedScenario);
        updatedScenario
            .nom(UPDATED_NOM);
        ScenarioDTO scenarioDTO = scenarioMapper.toDto(updatedScenario);

        restScenarioMockMvc.perform(put("/api/scenarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scenarioDTO)))
            .andExpect(status().isOk());

        // Validate the Scenario in the database
        List<Scenario> scenarioList = scenarioRepository.findAll();
        assertThat(scenarioList).hasSize(databaseSizeBeforeUpdate);
        Scenario testScenario = scenarioList.get(scenarioList.size() - 1);
        assertThat(testScenario.getNom()).isEqualTo(UPDATED_NOM);

        // Validate the Scenario in Elasticsearch
        verify(mockScenarioSearchRepository, times(1)).save(testScenario);
    }

    @Test
    @Transactional
    public void updateNonExistingScenario() throws Exception {
        int databaseSizeBeforeUpdate = scenarioRepository.findAll().size();

        // Create the Scenario
        ScenarioDTO scenarioDTO = scenarioMapper.toDto(scenario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScenarioMockMvc.perform(put("/api/scenarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scenarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scenario in the database
        List<Scenario> scenarioList = scenarioRepository.findAll();
        assertThat(scenarioList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Scenario in Elasticsearch
        verify(mockScenarioSearchRepository, times(0)).save(scenario);
    }

    @Test
    @Transactional
    public void deleteScenario() throws Exception {
        // Initialize the database
        scenarioRepository.saveAndFlush(scenario);

        int databaseSizeBeforeDelete = scenarioRepository.findAll().size();

        // Get the scenario
        restScenarioMockMvc.perform(delete("/api/scenarios/{id}", scenario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Scenario> scenarioList = scenarioRepository.findAll();
        assertThat(scenarioList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Scenario in Elasticsearch
        verify(mockScenarioSearchRepository, times(1)).deleteById(scenario.getId());
    }

    @Test
    @Transactional
    public void searchScenario() throws Exception {
        // Initialize the database
        scenarioRepository.saveAndFlush(scenario);
        when(mockScenarioSearchRepository.search(queryStringQuery("id:" + scenario.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(scenario), PageRequest.of(0, 1), 1));
        // Search the scenario
        restScenarioMockMvc.perform(get("/api/_search/scenarios?query=id:" + scenario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scenario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Scenario.class);
        Scenario scenario1 = new Scenario();
        scenario1.setId(1L);
        Scenario scenario2 = new Scenario();
        scenario2.setId(scenario1.getId());
        assertThat(scenario1).isEqualTo(scenario2);
        scenario2.setId(2L);
        assertThat(scenario1).isNotEqualTo(scenario2);
        scenario1.setId(null);
        assertThat(scenario1).isNotEqualTo(scenario2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScenarioDTO.class);
        ScenarioDTO scenarioDTO1 = new ScenarioDTO();
        scenarioDTO1.setId(1L);
        ScenarioDTO scenarioDTO2 = new ScenarioDTO();
        assertThat(scenarioDTO1).isNotEqualTo(scenarioDTO2);
        scenarioDTO2.setId(scenarioDTO1.getId());
        assertThat(scenarioDTO1).isEqualTo(scenarioDTO2);
        scenarioDTO2.setId(2L);
        assertThat(scenarioDTO1).isNotEqualTo(scenarioDTO2);
        scenarioDTO1.setId(null);
        assertThat(scenarioDTO1).isNotEqualTo(scenarioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(scenarioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(scenarioMapper.fromId(null)).isNull();
    }
}