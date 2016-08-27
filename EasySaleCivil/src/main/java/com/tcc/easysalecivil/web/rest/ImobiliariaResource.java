package com.tcc.easysalecivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tcc.easysalecivil.domain.Imobiliaria;
import com.tcc.easysalecivil.service.ImobiliariaService;
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
 * REST controller for managing Imobiliaria.
 */
@RestController
@RequestMapping("/api")
public class ImobiliariaResource {

    private final Logger log = LoggerFactory.getLogger(ImobiliariaResource.class);
        
    @Inject
    private ImobiliariaService imobiliariaService;
    
    /**
     * POST  /imobiliarias : Create a new imobiliaria.
     *
     * @param imobiliaria the imobiliaria to create
     * @return the ResponseEntity with status 201 (Created) and with body the new imobiliaria, or with status 400 (Bad Request) if the imobiliaria has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/imobiliarias",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Imobiliaria> createImobiliaria(@Valid @RequestBody Imobiliaria imobiliaria) throws URISyntaxException {
        log.debug("REST request to save Imobiliaria : {}", imobiliaria);
        if (imobiliaria.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("imobiliaria", "idexists", "A new imobiliaria cannot already have an ID")).body(null);
        }
        Imobiliaria result = imobiliariaService.save(imobiliaria);
        return ResponseEntity.created(new URI("/api/imobiliarias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("imobiliaria", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /imobiliarias : Updates an existing imobiliaria.
     *
     * @param imobiliaria the imobiliaria to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated imobiliaria,
     * or with status 400 (Bad Request) if the imobiliaria is not valid,
     * or with status 500 (Internal Server Error) if the imobiliaria couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/imobiliarias",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Imobiliaria> updateImobiliaria(@Valid @RequestBody Imobiliaria imobiliaria) throws URISyntaxException {
        log.debug("REST request to update Imobiliaria : {}", imobiliaria);
        if (imobiliaria.getId() == null) {
            return createImobiliaria(imobiliaria);
        }
        Imobiliaria result = imobiliariaService.save(imobiliaria);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("imobiliaria", imobiliaria.getId().toString()))
            .body(result);
    }

    /**
     * GET  /imobiliarias : get all the imobiliarias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of imobiliarias in body
     */
    @RequestMapping(value = "/imobiliarias",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Imobiliaria> getAllImobiliarias() {
        log.debug("REST request to get all Imobiliarias");
        return imobiliariaService.findAll();
    }

    /**
     * GET  /imobiliarias/:id : get the "id" imobiliaria.
     *
     * @param id the id of the imobiliaria to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the imobiliaria, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/imobiliarias/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Imobiliaria> getImobiliaria(@PathVariable Long id) {
        log.debug("REST request to get Imobiliaria : {}", id);
        Imobiliaria imobiliaria = imobiliariaService.findOne(id);
        return Optional.ofNullable(imobiliaria)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /imobiliarias/:id : delete the "id" imobiliaria.
     *
     * @param id the id of the imobiliaria to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/imobiliarias/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteImobiliaria(@PathVariable Long id) {
        log.debug("REST request to delete Imobiliaria : {}", id);
        imobiliariaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("imobiliaria", id.toString())).build();
    }

    /**
     * SEARCH  /_search/imobiliarias?query=:query : search for the imobiliaria corresponding
     * to the query.
     *
     * @param query the query of the imobiliaria search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/imobiliarias",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Imobiliaria> searchImobiliarias(@RequestParam String query) {
        log.debug("REST request to search Imobiliarias for query {}", query);
        return imobiliariaService.search(query);
    }


}
