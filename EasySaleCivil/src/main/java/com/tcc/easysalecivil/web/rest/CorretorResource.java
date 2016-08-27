package com.tcc.easysalecivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tcc.easysalecivil.domain.Corretor;
import com.tcc.easysalecivil.service.CorretorService;
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
 * REST controller for managing Corretor.
 */
@RestController
@RequestMapping("/api")
public class CorretorResource {

    private final Logger log = LoggerFactory.getLogger(CorretorResource.class);
        
    @Inject
    private CorretorService corretorService;
    
    /**
     * POST  /corretors : Create a new corretor.
     *
     * @param corretor the corretor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corretor, or with status 400 (Bad Request) if the corretor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/corretors",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Corretor> createCorretor(@Valid @RequestBody Corretor corretor) throws URISyntaxException {
        log.debug("REST request to save Corretor : {}", corretor);
        if (corretor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("corretor", "idexists", "A new corretor cannot already have an ID")).body(null);
        }
        Corretor result = corretorService.save(corretor);
        return ResponseEntity.created(new URI("/api/corretors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("corretor", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /corretors : Updates an existing corretor.
     *
     * @param corretor the corretor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corretor,
     * or with status 400 (Bad Request) if the corretor is not valid,
     * or with status 500 (Internal Server Error) if the corretor couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/corretors",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Corretor> updateCorretor(@Valid @RequestBody Corretor corretor) throws URISyntaxException {
        log.debug("REST request to update Corretor : {}", corretor);
        if (corretor.getId() == null) {
            return createCorretor(corretor);
        }
        Corretor result = corretorService.save(corretor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("corretor", corretor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /corretors : get all the corretors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corretors in body
     */
    @RequestMapping(value = "/corretors",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Corretor> getAllCorretors() {
        log.debug("REST request to get all Corretors");
        return corretorService.findAll();
    }

    /**
     * GET  /corretors/:id : get the "id" corretor.
     *
     * @param id the id of the corretor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corretor, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/corretors/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Corretor> getCorretor(@PathVariable Long id) {
        log.debug("REST request to get Corretor : {}", id);
        Corretor corretor = corretorService.findOne(id);
        return Optional.ofNullable(corretor)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /corretors/:id : delete the "id" corretor.
     *
     * @param id the id of the corretor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/corretors/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCorretor(@PathVariable Long id) {
        log.debug("REST request to delete Corretor : {}", id);
        corretorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("corretor", id.toString())).build();
    }

    /**
     * SEARCH  /_search/corretors?query=:query : search for the corretor corresponding
     * to the query.
     *
     * @param query the query of the corretor search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/corretors",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Corretor> searchCorretors(@RequestParam String query) {
        log.debug("REST request to search Corretors for query {}", query);
        return corretorService.search(query);
    }


}
