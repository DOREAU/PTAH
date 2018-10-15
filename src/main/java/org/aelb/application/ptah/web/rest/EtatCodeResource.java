package org.aelb.application.ptah.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.aelb.application.ptah.service.EtatCodeService;
import org.aelb.application.ptah.web.rest.errors.BadRequestAlertException;
import org.aelb.application.ptah.web.rest.util.HeaderUtil;
import org.aelb.application.ptah.service.dto.EtatCodeDTO;
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
 * REST controller for managing EtatCode.
 */
@RestController
@RequestMapping("/api")
public class EtatCodeResource {

    private final Logger log = LoggerFactory.getLogger(EtatCodeResource.class);

    private static final String ENTITY_NAME = "etatCode";

    private final EtatCodeService etatCodeService;

    public EtatCodeResource(EtatCodeService etatCodeService) {
        this.etatCodeService = etatCodeService;
    }

    /**
     * POST  /etat-codes : Create a new etatCode.
     *
     * @param etatCodeDTO the etatCodeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new etatCodeDTO, or with status 400 (Bad Request) if the etatCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/etat-codes")
    @Timed
    public ResponseEntity<EtatCodeDTO> createEtatCode(@RequestBody EtatCodeDTO etatCodeDTO) throws URISyntaxException {
        log.debug("REST request to save EtatCode : {}", etatCodeDTO);
        if (etatCodeDTO.getId() != null) {
            throw new BadRequestAlertException("A new etatCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtatCodeDTO result = etatCodeService.save(etatCodeDTO);
        return ResponseEntity.created(new URI("/api/etat-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /etat-codes : Updates an existing etatCode.
     *
     * @param etatCodeDTO the etatCodeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated etatCodeDTO,
     * or with status 400 (Bad Request) if the etatCodeDTO is not valid,
     * or with status 500 (Internal Server Error) if the etatCodeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/etat-codes")
    @Timed
    public ResponseEntity<EtatCodeDTO> updateEtatCode(@RequestBody EtatCodeDTO etatCodeDTO) throws URISyntaxException {
        log.debug("REST request to update EtatCode : {}", etatCodeDTO);
        if (etatCodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EtatCodeDTO result = etatCodeService.save(etatCodeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, etatCodeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /etat-codes : get all the etatCodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of etatCodes in body
     */
    @GetMapping("/etat-codes")
    @Timed
    public List<EtatCodeDTO> getAllEtatCodes() {
        log.debug("REST request to get all EtatCodes");
        return etatCodeService.findAll();
    }

    /**
     * GET  /etat-codes/:id : get the "id" etatCode.
     *
     * @param id the id of the etatCodeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the etatCodeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/etat-codes/{id}")
    @Timed
    public ResponseEntity<EtatCodeDTO> getEtatCode(@PathVariable Long id) {
        log.debug("REST request to get EtatCode : {}", id);
        Optional<EtatCodeDTO> etatCodeDTO = etatCodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etatCodeDTO);
    }

    /**
     * DELETE  /etat-codes/:id : delete the "id" etatCode.
     *
     * @param id the id of the etatCodeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/etat-codes/{id}")
    @Timed
    public ResponseEntity<Void> deleteEtatCode(@PathVariable Long id) {
        log.debug("REST request to delete EtatCode : {}", id);
        etatCodeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/etat-codes?query=:query : search for the etatCode corresponding
     * to the query.
     *
     * @param query the query of the etatCode search
     * @return the result of the search
     */
    @GetMapping("/_search/etat-codes")
    @Timed
    public List<EtatCodeDTO> searchEtatCodes(@RequestParam String query) {
        log.debug("REST request to search EtatCodes for query {}", query);
        return etatCodeService.search(query);
    }

}
