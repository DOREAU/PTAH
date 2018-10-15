package org.aelb.application.ptah.web.rest;

import org.aelb.application.ptah.PtahApp;

import org.aelb.application.ptah.domain.PageWeb;
import org.aelb.application.ptah.repository.PageWebRepository;
import org.aelb.application.ptah.repository.search.PageWebSearchRepository;
import org.aelb.application.ptah.service.PageWebService;
import org.aelb.application.ptah.service.dto.PageWebDTO;
import org.aelb.application.ptah.service.mapper.PageWebMapper;
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
 * Test class for the PageWebResource REST controller.
 *
 * @see PageWebResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PtahApp.class)
public class PageWebResourceIntTest {

    private static final String DEFAULT_URL_PAGE = "AAAAAAAAAA";
    private static final String UPDATED_URL_PAGE = "BBBBBBBBBB";

    @Autowired
    private PageWebRepository pageWebRepository;

    @Autowired
    private PageWebMapper pageWebMapper;
    
    @Autowired
    private PageWebService pageWebService;

    /**
     * This repository is mocked in the org.aelb.application.ptah.repository.search test package.
     *
     * @see org.aelb.application.ptah.repository.search.PageWebSearchRepositoryMockConfiguration
     */
    @Autowired
    private PageWebSearchRepository mockPageWebSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPageWebMockMvc;

    private PageWeb pageWeb;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PageWebResource pageWebResource = new PageWebResource(pageWebService);
        this.restPageWebMockMvc = MockMvcBuilders.standaloneSetup(pageWebResource)
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
    public static PageWeb createEntity(EntityManager em) {
        PageWeb pageWeb = new PageWeb()
            .urlPage(DEFAULT_URL_PAGE);
        return pageWeb;
    }

    @Before
    public void initTest() {
        pageWeb = createEntity(em);
    }

    @Test
    @Transactional
    public void createPageWeb() throws Exception {
        int databaseSizeBeforeCreate = pageWebRepository.findAll().size();

        // Create the PageWeb
        PageWebDTO pageWebDTO = pageWebMapper.toDto(pageWeb);
        restPageWebMockMvc.perform(post("/api/page-webs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageWebDTO)))
            .andExpect(status().isCreated());

        // Validate the PageWeb in the database
        List<PageWeb> pageWebList = pageWebRepository.findAll();
        assertThat(pageWebList).hasSize(databaseSizeBeforeCreate + 1);
        PageWeb testPageWeb = pageWebList.get(pageWebList.size() - 1);
        assertThat(testPageWeb.getUrlPage()).isEqualTo(DEFAULT_URL_PAGE);

        // Validate the PageWeb in Elasticsearch
        verify(mockPageWebSearchRepository, times(1)).save(testPageWeb);
    }

    @Test
    @Transactional
    public void createPageWebWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pageWebRepository.findAll().size();

        // Create the PageWeb with an existing ID
        pageWeb.setId(1L);
        PageWebDTO pageWebDTO = pageWebMapper.toDto(pageWeb);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPageWebMockMvc.perform(post("/api/page-webs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageWebDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PageWeb in the database
        List<PageWeb> pageWebList = pageWebRepository.findAll();
        assertThat(pageWebList).hasSize(databaseSizeBeforeCreate);

        // Validate the PageWeb in Elasticsearch
        verify(mockPageWebSearchRepository, times(0)).save(pageWeb);
    }

    @Test
    @Transactional
    public void getAllPageWebs() throws Exception {
        // Initialize the database
        pageWebRepository.saveAndFlush(pageWeb);

        // Get all the pageWebList
        restPageWebMockMvc.perform(get("/api/page-webs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageWeb.getId().intValue())))
            .andExpect(jsonPath("$.[*].urlPage").value(hasItem(DEFAULT_URL_PAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getPageWeb() throws Exception {
        // Initialize the database
        pageWebRepository.saveAndFlush(pageWeb);

        // Get the pageWeb
        restPageWebMockMvc.perform(get("/api/page-webs/{id}", pageWeb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pageWeb.getId().intValue()))
            .andExpect(jsonPath("$.urlPage").value(DEFAULT_URL_PAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPageWeb() throws Exception {
        // Get the pageWeb
        restPageWebMockMvc.perform(get("/api/page-webs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePageWeb() throws Exception {
        // Initialize the database
        pageWebRepository.saveAndFlush(pageWeb);

        int databaseSizeBeforeUpdate = pageWebRepository.findAll().size();

        // Update the pageWeb
        PageWeb updatedPageWeb = pageWebRepository.findById(pageWeb.getId()).get();
        // Disconnect from session so that the updates on updatedPageWeb are not directly saved in db
        em.detach(updatedPageWeb);
        updatedPageWeb
            .urlPage(UPDATED_URL_PAGE);
        PageWebDTO pageWebDTO = pageWebMapper.toDto(updatedPageWeb);

        restPageWebMockMvc.perform(put("/api/page-webs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageWebDTO)))
            .andExpect(status().isOk());

        // Validate the PageWeb in the database
        List<PageWeb> pageWebList = pageWebRepository.findAll();
        assertThat(pageWebList).hasSize(databaseSizeBeforeUpdate);
        PageWeb testPageWeb = pageWebList.get(pageWebList.size() - 1);
        assertThat(testPageWeb.getUrlPage()).isEqualTo(UPDATED_URL_PAGE);

        // Validate the PageWeb in Elasticsearch
        verify(mockPageWebSearchRepository, times(1)).save(testPageWeb);
    }

    @Test
    @Transactional
    public void updateNonExistingPageWeb() throws Exception {
        int databaseSizeBeforeUpdate = pageWebRepository.findAll().size();

        // Create the PageWeb
        PageWebDTO pageWebDTO = pageWebMapper.toDto(pageWeb);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageWebMockMvc.perform(put("/api/page-webs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageWebDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PageWeb in the database
        List<PageWeb> pageWebList = pageWebRepository.findAll();
        assertThat(pageWebList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PageWeb in Elasticsearch
        verify(mockPageWebSearchRepository, times(0)).save(pageWeb);
    }

    @Test
    @Transactional
    public void deletePageWeb() throws Exception {
        // Initialize the database
        pageWebRepository.saveAndFlush(pageWeb);

        int databaseSizeBeforeDelete = pageWebRepository.findAll().size();

        // Get the pageWeb
        restPageWebMockMvc.perform(delete("/api/page-webs/{id}", pageWeb.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PageWeb> pageWebList = pageWebRepository.findAll();
        assertThat(pageWebList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PageWeb in Elasticsearch
        verify(mockPageWebSearchRepository, times(1)).deleteById(pageWeb.getId());
    }

    @Test
    @Transactional
    public void searchPageWeb() throws Exception {
        // Initialize the database
        pageWebRepository.saveAndFlush(pageWeb);
        when(mockPageWebSearchRepository.search(queryStringQuery("id:" + pageWeb.getId())))
            .thenReturn(Collections.singletonList(pageWeb));
        // Search the pageWeb
        restPageWebMockMvc.perform(get("/api/_search/page-webs?query=id:" + pageWeb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageWeb.getId().intValue())))
            .andExpect(jsonPath("$.[*].urlPage").value(hasItem(DEFAULT_URL_PAGE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageWeb.class);
        PageWeb pageWeb1 = new PageWeb();
        pageWeb1.setId(1L);
        PageWeb pageWeb2 = new PageWeb();
        pageWeb2.setId(pageWeb1.getId());
        assertThat(pageWeb1).isEqualTo(pageWeb2);
        pageWeb2.setId(2L);
        assertThat(pageWeb1).isNotEqualTo(pageWeb2);
        pageWeb1.setId(null);
        assertThat(pageWeb1).isNotEqualTo(pageWeb2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageWebDTO.class);
        PageWebDTO pageWebDTO1 = new PageWebDTO();
        pageWebDTO1.setId(1L);
        PageWebDTO pageWebDTO2 = new PageWebDTO();
        assertThat(pageWebDTO1).isNotEqualTo(pageWebDTO2);
        pageWebDTO2.setId(pageWebDTO1.getId());
        assertThat(pageWebDTO1).isEqualTo(pageWebDTO2);
        pageWebDTO2.setId(2L);
        assertThat(pageWebDTO1).isNotEqualTo(pageWebDTO2);
        pageWebDTO1.setId(null);
        assertThat(pageWebDTO1).isNotEqualTo(pageWebDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pageWebMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pageWebMapper.fromId(null)).isNull();
    }
}
