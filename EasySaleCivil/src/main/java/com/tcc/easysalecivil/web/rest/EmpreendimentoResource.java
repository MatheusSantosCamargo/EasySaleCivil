package com.tcc.easysalecivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tcc.easysalecivil.domain.Empreendimento;
import com.tcc.easysalecivil.service.EmpreendimentoService;
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
 * REST controller for managing Empreendimento.
 */
@RestController
@RequestMapping("/api")
public class EmpreendimentoResource {

    private final Logger log = LoggerFactory.getLogger(EmpreendimentoResource.class);
        
    @Inject
    private EmpreendimentoService empreendimentoService;
    
    /**
     * POST  /empreendimentos : Create a new empreendimento.
     *
     * @param empreendimento the empreendimento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new empreendimento, or with status 400 (Bad Request) if the empreendimento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/empreendimentos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Empreendimento> createEmpreendimento(@Valid @RequestBody Empreendimento empreendimento) throws URISyntaxException {
        log.debug("REST request to save Empreendimento : {}", empreendimento);
        if (empreendimento.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("empreendimento", "idexists", "A new empreendimento cannot already have an ID")).body(null);
        }
        Empreendimento result = empreendimentoService.save(empreendimento);
        return ResponseEntity.created(new URI("/api/empreendimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("empreendimento", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /empreendimentos : Updates an existing empreendimento.
     *
     * @param empreendimento the empreendimento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated empreendimento,
     * or with status 400 (Bad Request) if the empreendimento is not valid,
     * or with status 500 (Internal Server Error) if the empreendimento couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/empreendimentos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Empreendimento> updateEmpreendimento(@Valid @RequestBody Empreendimento empreendimento) throws URISyntaxException {
        log.debug("REST request to update Empreendimento : {}", empreendimento);
        if (empreendimento.getId() == null) {
            return createEmpreendimento(empreendimento);
        }
        Empreendimento result = empreendimentoService.save(empreendimento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("empreendimento", empreendimento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /empreendimentos : get all the empreendimentos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of empreendimentos in body
     */
    @RequestMapping(value = "/empreendimentos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Empreendimento> getAllEmpreendimentos() {
        log.debug("REST request to get all Empreendimentos");
        return empreendimentoService.findAll();
    }

    /**
     * GET  /empreendimentos/:id : get the "id" empreendimento.
     *
     * @param id the id of the empreendimento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the empreendimento, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/empreendimentos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Empreendimento> getEmpreendimento(@PathVariable("id") Long id) {
        log.debug("REST request to get Empreendimento : {}", id);
        Empreendimento empreendimento = empreendimentoService.findOne(id);
        return Optional.ofNullable(empreendimento)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /empreendimentos/:id : delete the "id" empreendimento.
     *
     * @param id the id of the empreendimento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/empreendimentos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEmpreendimento(@PathVariable Long id) {
        log.debug("REST request to delete Empreendimento : {}", id);
        empreendimentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("empreendimento", id.toString())).build();
    }

    /**
     * SEARCH  /_search/empreendimentos?query=:query : search for the empreendimento corresponding
     * to the query.
     *
     * @param query the query of the empreendimento search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/empreendimentos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Empreendimento> searchEmpreendimentos(@RequestParam String query) {
        log.debug("REST request to search Empreendimentos for query {}", query);
        return empreendimentoService.search(query);
    }


}
