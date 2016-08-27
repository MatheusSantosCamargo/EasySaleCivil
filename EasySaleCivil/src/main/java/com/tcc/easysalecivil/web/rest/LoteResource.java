package com.tcc.easysalecivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tcc.easysalecivil.domain.Empreendimento;
import com.tcc.easysalecivil.domain.Imobiliaria;
import com.tcc.easysalecivil.domain.Lote;
import com.tcc.easysalecivil.domain.SituacaoLote;
import com.tcc.easysalecivil.service.EmpreendimentoService;
import com.tcc.easysalecivil.service.LoteService;
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
 * REST controller for managing Lote.
 */
@RestController
@RequestMapping("/api")
public class LoteResource {

    private final Logger log = LoggerFactory.getLogger(LoteResource.class);
        
    @Inject
    private LoteService loteService;

    @Inject
    private SituacaoLoteService situacaoLote;
    
    @Inject
    private EmpreendimentoService emp;
    
    /**
     * POST  /lotes : Create a new lote.
     *
     * @param lote the lote to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lote, or with status 400 (Bad Request) if the lote has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/lotes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lote> createLote(@Valid @RequestBody Lote lote) throws URISyntaxException {
        log.debug("REST request to save Lote : {}", lote);
        if (lote.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lote", "idexists", "A new lote cannot already have an ID")).body(null);
        }
        Lote result = loteService.save(lote);
        return ResponseEntity.created(new URI("/api/lotes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lote", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lotes : Updates an existing lote.
     *
     * @param lote the lote to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lote,
     * or with status 400 (Bad Request) if the lote is not valid,
     * or with status 500 (Internal Server Error) if the lote couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/lotes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lote> updateLote(@Valid @RequestBody Lote lote) throws URISyntaxException {
        log.debug("REST request to update Lote : {}", lote);
        if (lote.getId() == null) {
            return createLote(lote);
        }
        Lote result = loteService.save(lote);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lote", lote.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lotes : get all the lotes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lotes in body
     */
    @RequestMapping(value = "/lotes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Lote> getAllLotes() {
        log.debug("REST request to get all Lotes");
        return loteService.findAll();
    }

    /**
     * GET  /lotes/:id : get the "id" lote.
     *
     * @param id the id of the lote to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lote, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/lotes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lote> getLote(@PathVariable Long id) {
        log.debug("REST request to get Lote : {}", id);
        Lote lote = loteService.findOne(id);
        return Optional.ofNullable(lote)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    /**
     * GET  /lotes/lotesDisponiveis : retorna todos os lotes com situacao disponivel.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lotes in body
     */
    @RequestMapping(value = "/lotes/lotesDisponiveis",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Lote> getAllLotesDisponiveis() {
        log.debug("REST retorna todos os lotes com situacao disponivel");
        SituacaoLote situacao = situacaoLote.findOne(1L);
        return loteService.findBySituacaoLoteIs(situacao);
    }
    
    /**
     * GET  /lotesPorEmpreendimento/:idEmpreendimento : retorna todos os lotes para o empreendimento escolhido.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lotes in body
     */
    @RequestMapping(value = "/lotesPorEmpreendimento/{idEmpreendimento}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Lote> getAllLotesPorEmpreendimento(@PathVariable Long idEmpreendimento) {
        log.debug("REST retorna todos os lotes para o emprendimento: {}", idEmpreendimento);
        Empreendimento empreendimento = emp.findOne(idEmpreendimento);
        return loteService.findByEmpreendimento(empreendimento);

    }

    /**
     * DELETE  /lotes/:id : delete the "id" lote.
     *
     * @param id the id of the lote to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/lotes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLote(@PathVariable Long id) {
        log.debug("REST request to delete Lote : {}", id);
        loteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lote", id.toString())).build();
    }

    /**
     * SEARCH  /_search/lotes?query=:query : search for the lote corresponding
     * to the query.
     *
     * @param query the query of the lote search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/lotes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Lote> searchLotes(@RequestParam String query) {
        log.debug("REST request to search Lotes for query {}", query);
        return loteService.search(query);
    }


}
