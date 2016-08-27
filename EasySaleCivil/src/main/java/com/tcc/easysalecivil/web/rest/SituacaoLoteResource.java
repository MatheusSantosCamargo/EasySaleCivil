package com.tcc.easysalecivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tcc.easysalecivil.domain.SituacaoLote;
import com.tcc.easysalecivil.service.SituacaoLoteService;
import com.tcc.easysalecivil.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing SituacaoLote.
 */
@RestController
@RequestMapping("/api")
public class SituacaoLoteResource {

    private final Logger log = LoggerFactory.getLogger(SituacaoLoteResource.class);
        
    @Inject
    private SituacaoLoteService situacaoLoteService;
    
    /**
     * POST  /situacao-lotes : Create a new situacaoLote.
     *
     * @param situacaoLote the situacaoLote to create
     * @return the ResponseEntity with status 201 (Created) and with body the new situacaoLote, or with status 400 (Bad Request) if the situacaoLote has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/situacao-lotes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SituacaoLote> createSituacaoLote(@Valid @RequestBody SituacaoLote situacaoLote) throws URISyntaxException {
        log.debug("REST request to save SituacaoLote : {}", situacaoLote);
        if (situacaoLote.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("situacaoLote", "idexists", "A new situacaoLote cannot already have an ID")).body(null);
        }
        SituacaoLote result = situacaoLoteService.save(situacaoLote);
        return ResponseEntity.created(new URI("/api/situacao-lotes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("situacaoLote", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /situacao-lotes : Updates an existing situacaoLote.
     *
     * @param situacaoLote the situacaoLote to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated situacaoLote,
     * or with status 400 (Bad Request) if the situacaoLote is not valid,
     * or with status 500 (Internal Server Error) if the situacaoLote couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/situacao-lotes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SituacaoLote> updateSituacaoLote(@Valid @RequestBody SituacaoLote situacaoLote) throws URISyntaxException {
        log.debug("REST request to update SituacaoLote : {}", situacaoLote);
        if (situacaoLote.getId() == null) {
            return createSituacaoLote(situacaoLote);
        }
        SituacaoLote result = situacaoLoteService.save(situacaoLote);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("situacaoLote", situacaoLote.getId().toString()))
            .body(result);
    }

    /**
     * GET  /situacao-lotes : get all the situacaoLotes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of situacaoLotes in body
     */
    @RequestMapping(value = "/situacao-lotes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SituacaoLote> getAllSituacaoLotes() {
        log.debug("REST request to get all SituacaoLotes");
        return situacaoLoteService.findAll();
    }

    /**
     * GET  /situacao-lotes/:id : get the "id" situacaoLote.
     *
     * @param id the id of the situacaoLote to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the situacaoLote, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/situacao-lotes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SituacaoLote> getSituacaoLote(@PathVariable Long id) {
        log.debug("REST request to get SituacaoLote : {}", id);
        SituacaoLote situacaoLote = situacaoLoteService.findOne(id);
        return Optional.ofNullable(situacaoLote)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /situacao-lotes/:id : delete the "id" situacaoLote.
     *
     * @param id the id of the situacaoLote to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/situacao-lotes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSituacaoLote(@PathVariable Long id) {
        log.debug("REST request to delete SituacaoLote : {}", id);
        situacaoLoteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("situacaoLote", id.toString())).build();
    }

    /**
     * SEARCH  /_search/situacao-lotes?query=:query : search for the situacaoLote corresponding
     * to the query.
     *
     * @param query the query of the situacaoLote search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/situacao-lotes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SituacaoLote> searchSituacaoLotes(@RequestParam String query) {
        log.debug("REST request to search SituacaoLotes for query {}", query);
        return situacaoLoteService.search(query);
    }


}
