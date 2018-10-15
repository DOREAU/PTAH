package org.aelb.application.ptah.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.aelb.application.ptah.service.CodeAccesService;
import org.aelb.application.ptah.web.rest.errors.BadRequestAlertException;
import org.aelb.application.ptah.web.rest.util.HeaderUtil;
import org.aelb.application.ptah.service.dto.CodeAccesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CodeAcces.
 */
@RestController
@RequestMapping("/api")
public class CodeAccesResource {

    private final Logger log = LoggerFactory.getLogger(CodeAccesResource.class);

    private static final String ENTITY_NAME = "codeAcces";

    private final CodeAccesService codeAccesService;

    public CodeAccesResource(CodeAccesService codeAccesService) {
        this.codeAccesService = codeAccesService;
    }

    /**
     * POST  /code-acces : Create a new codeAcces.
     *
     * @param codeAccesDTO the codeAccesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new codeAccesDTO, or with status 400 (Bad Request) if the codeAcces has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/code-acces")
    @Timed
    public ResponseEntity<CodeAccesDTO> createCodeAcces(@RequestBody CodeAccesDTO codeAccesDTO) throws URISyntaxException {
        log.debug("REST request to save CodeAcces : {}", codeAccesDTO);
        if (codeAccesDTO.getId() != null) {
            throw new BadRequestAlertException("A new codeAcces cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CodeAccesDTO result = codeAccesService.save(codeAccesDTO);
        return ResponseEntity.created(new URI("/api/code-acces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /code-acces : Updates an existing codeAcces.
     *
     * @param codeAccesDTO the codeAccesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated codeAccesDTO,
     * or with status 400 (Bad Request) if the codeAccesDTO is not valid,
     * or with status 500 (Internal Server Error) if the codeAccesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/code-acces")
    @Timed
    public ResponseEntity<CodeAccesDTO> updateCodeAcces(@RequestBody CodeAccesDTO codeAccesDTO) throws URISyntaxException {
        log.debug("REST request to update CodeAcces : {}", codeAccesDTO);
        if (codeAccesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CodeAccesDTO result = codeAccesService.save(codeAccesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, codeAccesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /code-acces : get all the codeAcces.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of codeAcces in body
     */
    @GetMapping("/code-acces")
    @Timed
    public List<CodeAccesDTO> getAllCodeAcces() {
        log.debug("REST request to get all CodeAcces");
        return codeAccesService.findAll();
    }

    /**
     * GET  /code-acces/:id : get the "id" codeAcces.
     *
     * @param id the id of the codeAccesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the codeAccesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/code-acces/{id}")
    @Timed
    public ResponseEntity<CodeAccesDTO> getCodeAcces(@PathVariable Long id) {
        log.debug("REST request to get CodeAcces : {}", id);
        Optional<CodeAccesDTO> codeAccesDTO = codeAccesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(codeAccesDTO);
    }

    /**
     * DELETE  /code-acces/:id : delete the "id" codeAcces.
     *
     * @param id the id of the codeAccesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/code-acces/{id}")
    @Timed
    public ResponseEntity<Void> deleteCodeAcces(@PathVariable Long id) {
        log.debug("REST request to delete CodeAcces : {}", id);
        codeAccesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/code-acces?query=:query : search for the codeAcces corresponding
     * to the query.
     *
     * @param query the query of the codeAcces search
     * @return the result of the search
     */
    @GetMapping("/_search/code-acces")
    @Timed
    public List<CodeAccesDTO> searchCodeAcces(@RequestParam String query) {
        log.debug("REST request to search CodeAcces for query {}", query);
        return codeAccesService.search(query);
    }

}
