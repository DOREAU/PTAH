package org.aelb.application.ptah.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.aelb.application.ptah.service.PageWebService;
import org.aelb.application.ptah.web.rest.errors.BadRequestAlertException;
import org.aelb.application.ptah.web.rest.util.HeaderUtil;
import org.aelb.application.ptah.service.dto.PageWebDTO;
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
 * REST controller for managing PageWeb.
 */
@RestController
@RequestMapping("/api")
public class PageWebResource {

    private final Logger log = LoggerFactory.getLogger(PageWebResource.class);

    private static final String ENTITY_NAME = "pageWeb";

    private final PageWebService pageWebService;

    public PageWebResource(PageWebService pageWebService) {
        this.pageWebService = pageWebService;
    }

    /**
     * POST  /page-webs : Create a new pageWeb.
     *
     * @param pageWebDTO the pageWebDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pageWebDTO, or with status 400 (Bad Request) if the pageWeb has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/page-webs")
    @Timed
    public ResponseEntity<PageWebDTO> createPageWeb(@RequestBody PageWebDTO pageWebDTO) throws URISyntaxException {
        log.debug("REST request to save PageWeb : {}", pageWebDTO);
        if (pageWebDTO.getId() != null) {
            throw new BadRequestAlertException("A new pageWeb cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PageWebDTO result = pageWebService.save(pageWebDTO);
        return ResponseEntity.created(new URI("/api/page-webs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /page-webs : Updates an existing pageWeb.
     *
     * @param pageWebDTO the pageWebDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pageWebDTO,
     * or with status 400 (Bad Request) if the pageWebDTO is not valid,
     * or with status 500 (Internal Server Error) if the pageWebDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/page-webs")
    @Timed
    public ResponseEntity<PageWebDTO> updatePageWeb(@RequestBody PageWebDTO pageWebDTO) throws URISyntaxException {
        log.debug("REST request to update PageWeb : {}", pageWebDTO);
        if (pageWebDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PageWebDTO result = pageWebService.save(pageWebDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pageWebDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /page-webs : get all the pageWebs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pageWebs in body
     */
    @GetMapping("/page-webs")
    @Timed
    public List<PageWebDTO> getAllPageWebs() {
        log.debug("REST request to get all PageWebs");
        return pageWebService.findAll();
    }

    /**
     * GET  /page-webs/:id : get the "id" pageWeb.
     *
     * @param id the id of the pageWebDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pageWebDTO, or with status 404 (Not Found)
     */
    @GetMapping("/page-webs/{id}")
    @Timed
    public ResponseEntity<PageWebDTO> getPageWeb(@PathVariable Long id) {
        log.debug("REST request to get PageWeb : {}", id);
        Optional<PageWebDTO> pageWebDTO = pageWebService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pageWebDTO);
    }

    /**
     * DELETE  /page-webs/:id : delete the "id" pageWeb.
     *
     * @param id the id of the pageWebDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/page-webs/{id}")
    @Timed
    public ResponseEntity<Void> deletePageWeb(@PathVariable Long id) {
        log.debug("REST request to delete PageWeb : {}", id);
        pageWebService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/page-webs?query=:query : search for the pageWeb corresponding
     * to the query.
     *
     * @param query the query of the pageWeb search
     * @return the result of the search
     */
    @GetMapping("/_search/page-webs")
    @Timed
    public List<PageWebDTO> searchPageWebs(@RequestParam String query) {
        log.debug("REST request to search PageWebs for query {}", query);
        return pageWebService.search(query);
    }

}
