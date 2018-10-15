package org.aelb.application.ptah.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.aelb.application.ptah.service.ScenarioService;
import org.aelb.application.ptah.web.rest.errors.BadRequestAlertException;
import org.aelb.application.ptah.web.rest.util.HeaderUtil;
import org.aelb.application.ptah.web.rest.util.PaginationUtil;
import org.aelb.application.ptah.service.dto.ScenarioDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Scenario.
 */
@RestController
@RequestMapping("/api")
public class ScenarioResource {

    private final Logger log = LoggerFactory.getLogger(ScenarioResource.class);

    private static final String ENTITY_NAME = "scenario";

    private final ScenarioService scenarioService;

    public ScenarioResource(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    /**
     * POST  /scenarios : Create a new scenario.
     *
     * @param scenarioDTO the scenarioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new scenarioDTO, or with status 400 (Bad Request) if the scenario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/scenarios")
    @Timed
    public ResponseEntity<ScenarioDTO> createScenario(@RequestBody ScenarioDTO scenarioDTO) throws URISyntaxException {
        log.debug("REST request to save Scenario : {}", scenarioDTO);
        if (scenarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new scenario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScenarioDTO result = scenarioService.save(scenarioDTO);
        return ResponseEntity.created(new URI("/api/scenarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /scenarios : Updates an existing scenario.
     *
     * @param scenarioDTO the scenarioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated scenarioDTO,
     * or with status 400 (Bad Request) if the scenarioDTO is not valid,
     * or with status 500 (Internal Server Error) if the scenarioDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/scenarios")
    @Timed
    public ResponseEntity<ScenarioDTO> updateScenario(@RequestBody ScenarioDTO scenarioDTO) throws URISyntaxException {
        log.debug("REST request to update Scenario : {}", scenarioDTO);
        if (scenarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScenarioDTO result = scenarioService.save(scenarioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, scenarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /scenarios : get all the scenarios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of scenarios in body
     */
    @GetMapping("/scenarios")
    @Timed
    public ResponseEntity<List<ScenarioDTO>> getAllScenarios(Pageable pageable) {
        log.debug("REST request to get a page of Scenarios");
        Page<ScenarioDTO> page = scenarioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/scenarios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /scenarios/:id : get the "id" scenario.
     *
     * @param id the id of the scenarioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the scenarioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/scenarios/{id}")
    @Timed
    public ResponseEntity<ScenarioDTO> getScenario(@PathVariable Long id) {
        log.debug("REST request to get Scenario : {}", id);
        Optional<ScenarioDTO> scenarioDTO = scenarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scenarioDTO);
    }

    /**
     * DELETE  /scenarios/:id : delete the "id" scenario.
     *
     * @param id the id of the scenarioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/scenarios/{id}")
    @Timed
    public ResponseEntity<Void> deleteScenario(@PathVariable Long id) {
        log.debug("REST request to delete Scenario : {}", id);
        scenarioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/scenarios?query=:query : search for the scenario corresponding
     * to the query.
     *
     * @param query the query of the scenario search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/scenarios")
    @Timed
    public ResponseEntity<List<ScenarioDTO>> searchScenarios(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Scenarios for query {}", query);
        Page<ScenarioDTO> page = scenarioService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/scenarios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
