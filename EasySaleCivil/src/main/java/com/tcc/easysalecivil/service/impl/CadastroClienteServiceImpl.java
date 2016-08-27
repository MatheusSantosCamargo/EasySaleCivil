package com.tcc.easysalecivil.service.impl;

import com.tcc.easysalecivil.service.CadastroClienteService;
import com.tcc.easysalecivil.domain.CadastroCliente;
import com.tcc.easysalecivil.repository.CadastroClienteRepository;
import com.tcc.easysalecivil.repository.search.CadastroClienteSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CadastroCliente.
 */
@Service
@Transactional
public class CadastroClienteServiceImpl implements CadastroClienteService{

    private final Logger log = LoggerFactory.getLogger(CadastroClienteServiceImpl.class);
    
    @Inject
    private CadastroClienteRepository cadastroClienteRepository;
    
    @Inject
    private CadastroClienteSearchRepository cadastroClienteSearchRepository;
    
    /**
     * Save a cadastroCliente.
     * 
     * @param cadastroCliente the entity to save
     * @return the persisted entity
     */
    public CadastroCliente save(CadastroCliente cadastroCliente) {
        log.debug("Request to save CadastroCliente : {}", cadastroCliente);
        CadastroCliente result = cadastroClienteRepository.save(cadastroCliente);
        cadastroClienteSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the cadastroClientes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<CadastroCliente> findAll() {
        log.debug("Request to get all CadastroClientes");
        List<CadastroCliente> result = cadastroClienteRepository.findAll();
        return result;
    }

    /**
     *  Get one cadastroCliente by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CadastroCliente findOne(Long id) {
        log.debug("Request to get CadastroCliente : {}", id);
        CadastroCliente cadastroCliente = cadastroClienteRepository.findOne(id);
        return cadastroCliente;
    }

    /**
     *  Delete the  cadastroCliente by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CadastroCliente : {}", id);
        cadastroClienteRepository.delete(id);
        cadastroClienteSearchRepository.delete(id);
    }

    /**
     * Search for the cadastroCliente corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CadastroCliente> search(String query) {
        log.debug("Request to search CadastroClientes for query {}", query);
        return StreamSupport
            .stream(cadastroClienteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
