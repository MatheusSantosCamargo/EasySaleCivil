package com.tcc.easysalecivil.service.impl;

import com.tcc.easysalecivil.service.CorretorService;
import com.tcc.easysalecivil.domain.Corretor;
import com.tcc.easysalecivil.repository.CorretorRepository;
import com.tcc.easysalecivil.repository.search.CorretorSearchRepository;
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
 * Service Implementation for managing Corretor.
 */
@Service
@Transactional
public class CorretorServiceImpl implements CorretorService{

    private final Logger log = LoggerFactory.getLogger(CorretorServiceImpl.class);
    
    @Inject
    private CorretorRepository corretorRepository;
    
    @Inject
    private CorretorSearchRepository corretorSearchRepository;
    
    /**
     * Save a corretor.
     * 
     * @param corretor the entity to save
     * @return the persisted entity
     */
    public Corretor save(Corretor corretor) {
        log.debug("Request to save Corretor : {}", corretor);
        Corretor result = corretorRepository.save(corretor);
        corretorSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the corretors.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Corretor> findAll() {
        log.debug("Request to get all Corretors");
        List<Corretor> result = corretorRepository.findAll();
        return result;
    }

    /**
     *  Get one corretor by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Corretor findOne(Long id) {
        log.debug("Request to get Corretor : {}", id);
        Corretor corretor = corretorRepository.findOne(id);
        return corretor;
    }

    /**
     *  Delete the  corretor by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Corretor : {}", id);
        corretorRepository.delete(id);
        corretorSearchRepository.delete(id);
    }

    /**
     * Search for the corretor corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Corretor> search(String query) {
        log.debug("Request to search Corretors for query {}", query);
        return StreamSupport
            .stream(corretorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
