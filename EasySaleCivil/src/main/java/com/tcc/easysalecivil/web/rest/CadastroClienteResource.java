package com.tcc.easysalecivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tcc.easysalecivil.domain.CadastroCliente;
import com.tcc.easysalecivil.service.CadastroClienteService;
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
 * REST controller for managing CadastroCliente.
 */
@RestController
@RequestMapping("/api")
public class CadastroClienteResource {

    private final Logger log = LoggerFactory.getLogger(CadastroClienteResource.class);
        
    @Inject
    private CadastroClienteService cadastroClienteService;
    
    /**
     * POST  /cadastro-clientes : Create a new cadastroCliente.
     *
     * @param cadastroCliente the cadastroCliente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cadastroCliente, or with status 400 (Bad Request) if the cadastroCliente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cadastro-clientes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CadastroCliente> createCadastroCliente(@Valid @RequestBody CadastroCliente cadastroCliente) throws URISyntaxException {
        log.debug("REST request to save CadastroCliente : {}", cadastroCliente);
        if (cadastroCliente.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cadastroCliente", "idexists", "A new cadastroCliente cannot already have an ID")).body(null);
        }
        CadastroCliente result = cadastroClienteService.save(cadastroCliente);
        return ResponseEntity.created(new URI("/api/cadastro-clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cadastroCliente", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cadastro-clientes : Updates an existing cadastroCliente.
     *
     * @param cadastroCliente the cadastroCliente to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cadastroCliente,
     * or with status 400 (Bad Request) if the cadastroCliente is not valid,
     * or with status 500 (Internal Server Error) if the cadastroCliente couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cadastro-clientes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CadastroCliente> updateCadastroCliente(@Valid @RequestBody CadastroCliente cadastroCliente) throws URISyntaxException {
        log.debug("REST request to update CadastroCliente : {}", cadastroCliente);
        if (cadastroCliente.getId() == null) {
            return createCadastroCliente(cadastroCliente);
        }
        CadastroCliente result = cadastroClienteService.save(cadastroCliente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cadastroCliente", cadastroCliente.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cadastro-clientes : get all the cadastroClientes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cadastroClientes in body
     */
    @RequestMapping(value = "/cadastro-clientes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CadastroCliente> getAllCadastroClientes() {
        log.debug("REST request to get all CadastroClientes");
        return cadastroClienteService.findAll();
    }

    /**
     * GET  /cadastro-clientes/:id : get the "id" cadastroCliente.
     *
     * @param id the id of the cadastroCliente to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cadastroCliente, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/cadastro-clientes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CadastroCliente> getCadastroCliente(@PathVariable Long id) {
        log.debug("REST request to get CadastroCliente : {}", id);
        CadastroCliente cadastroCliente = cadastroClienteService.findOne(id);
        return Optional.ofNullable(cadastroCliente)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cadastro-clientes/:id : delete the "id" cadastroCliente.
     *
     * @param id the id of the cadastroCliente to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/cadastro-clientes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCadastroCliente(@PathVariable Long id) {
        log.debug("REST request to delete CadastroCliente : {}", id);
        cadastroClienteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cadastroCliente", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cadastro-clientes?query=:query : search for the cadastroCliente corresponding
     * to the query.
     *
     * @param query the query of the cadastroCliente search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/cadastro-clientes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CadastroCliente> searchCadastroClientes(@RequestParam String query) {
        log.debug("REST request to search CadastroClientes for query {}", query);
        return cadastroClienteService.search(query);
    }


}
