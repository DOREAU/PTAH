package org.aelb.application.ptah.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.aelb.application.ptah.service.LienPageWebService;
import org.aelb.application.ptah.web.rest.errors.BadRequestAlertException;
import org.aelb.application.ptah.web.rest.util.HeaderUtil;
import org.aelb.application.ptah.service.dto.LienPageWebDTO;
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
 * REST controller for managing LienPageWeb.
 */
@RestController
@RequestMapping("/api")
public class LienPageWebResource {

    private final Logger log = LoggerFactory.getLogger(LienPageWebResource.class);

    private static final String ENTITY_NAME = "lienPageWeb";

    private final LienPageWebService lienPageWebService;

    public LienPageWebResource(LienPageWebService lienPageWebService) {
        this.lienPageWebService = lienPageWebService;
    }

    /**
     * POST  /lien-page-webs : Create a new lienPageWeb.
     *
     * @param lienPageWebDTO the lienPageWebDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lienPageWebDTO, or with status 400 (Bad Request) if the lienPageWeb has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lien-page-webs")
    @Timed
    public ResponseEntity<LienPageWebDTO> createLienPageWeb(@RequestBody LienPageWebDTO lienPageWebDTO) throws URISyntaxException {
        log.debug("REST request to save LienPageWeb : {}", lienPageWebDTO);
        if (lienPageWebDTO.getId() != null) {
            throw new BadRequestAlertException("A new lienPageWeb cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LienPageWebDTO result = lienPageWebService.save(lienPageWebDTO);
        return ResponseEntity.created(new URI("/api/lien-page-webs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lien-page-webs : Updates an existing lienPageWeb.
     *
     * @param lienPageWebDTO the lienPageWebDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lienPageWebDTO,
     * or with status 400 (Bad Request) if the lienPageWebDTO is not valid,
     * or with status 500 (Internal Server Error) if the lienPageWebDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lien-page-webs")
    @Timed
    public ResponseEntity<LienPageWebDTO> updateLienPageWeb(@RequestBody LienPageWebDTO lienPageWebDTO) throws URISyntaxException {
        log.debug("REST request to update LienPageWeb : {}", lienPageWebDTO);
        if (lienPageWebDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LienPageWebDTO result = lienPageWebService.save(lienPageWebDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lienPageWebDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lien-page-webs : get all the lienPageWebs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lienPageWebs in body
     */
    @GetMapping("/lien-page-webs")
    @Timed
    public List<LienPageWebDTO> getAllLienPageWebs() {
        log.debug("REST request to get all LienPageWebs");
        return lienPageWebService.findAll();
    }

    /**
     * GET  /lien-page-webs/:id : get the "id" lienPageWeb.
     *
     * @param id the id of the lienPageWebDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lienPageWebDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lien-page-webs/{id}")
    @Timed
    public ResponseEntity<LienPageWebDTO> getLienPageWeb(@PathVariable Long id) {
        log.debug("REST request to get LienPageWeb : {}", id);
        Optional<LienPageWebDTO> lienPageWebDTO = lienPageWebService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lienPageWebDTO);
    }

    /**
     * DELETE  /lien-page-webs/:id : delete the "id" lienPageWeb.
     *
     * @param id the id of the lienPageWebDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lien-page-webs/{id}")
    @Timed
    public ResponseEntity<Void> deleteLienPageWeb(@PathVariable Long id) {
        log.debug("REST request to delete LienPageWeb : {}", id);
        lienPageWebService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/lien-page-webs?query=:query : search for the lienPageWeb corresponding
     * to the query.
     *
     * @param query the query of the lienPageWeb search
     * @return the result of the search
     */
    @GetMapping("/_search/lien-page-webs")
    @Timed
    public List<LienPageWebDTO> searchLienPageWebs(@RequestParam String query) {
        log.debug("REST request to search LienPageWebs for query {}", query);
        return lienPageWebService.search(query);
    }

}
