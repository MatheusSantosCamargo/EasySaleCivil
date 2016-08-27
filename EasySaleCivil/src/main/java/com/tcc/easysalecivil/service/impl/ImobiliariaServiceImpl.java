package com.tcc.easysalecivil.service.impl;

import com.tcc.easysalecivil.service.ImobiliariaService;
import com.tcc.easysalecivil.domain.Imobiliaria;
import com.tcc.easysalecivil.repository.ImobiliariaRepository;
import com.tcc.easysalecivil.repository.search.ImobiliariaSearchRepository;
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
 * Service Implementation for managing Imobiliaria.
 */
@Service
@Transactional
public class ImobiliariaServiceImpl implements ImobiliariaService{

    private final Logger log = LoggerFactory.getLogger(ImobiliariaServiceImpl.class);
    
    @Inject
    private ImobiliariaRepository imobiliariaRepository;
    
    @Inject
    private ImobiliariaSearchRepository imobiliariaSearchRepository;
    
    /**
     * Save a imobiliaria.
     * 
     * @param imobiliaria the entity to save
     * @return the persisted entity
     */
    public Imobiliaria save(Imobiliaria imobiliaria) {
        log.debug("Request to save Imobiliaria : {}", imobiliaria);
        Imobiliaria result = imobiliariaRepository.save(imobiliaria);
        imobiliariaSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the imobiliarias.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Imobiliaria> findAll() {
        log.debug("Request to get all Imobiliarias");
        List<Imobiliaria> result = imobiliariaRepository.findAll();
        return result;
    }

    /**
     *  Get one imobiliaria by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Imobiliaria findOne(Long id) {
        log.debug("Request to get Imobiliaria : {}", id);
        Imobiliaria imobiliaria = imobiliariaRepository.findOne(id);
        return imobiliaria;
    }

    /**
     *  Delete the  imobiliaria by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Imobiliaria : {}", id);
        imobiliariaRepository.delete(id);
        imobiliariaSearchRepository.delete(id);
    }

    /**
     * Search for the imobiliaria corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Imobiliaria> search(String query) {
        log.debug("Request to search Imobiliarias for query {}", query);
        return StreamSupport
            .stream(imobiliariaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
