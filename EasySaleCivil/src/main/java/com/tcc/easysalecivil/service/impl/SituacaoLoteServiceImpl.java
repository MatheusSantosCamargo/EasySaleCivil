package com.tcc.easysalecivil.service.impl;

import com.tcc.easysalecivil.service.SituacaoLoteService;
import com.tcc.easysalecivil.domain.SituacaoLote;
import com.tcc.easysalecivil.repository.SituacaoLoteRepository;
import com.tcc.easysalecivil.repository.search.SituacaoLoteSearchRepository;
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
 * Service Implementation for managing SituacaoLote.
 */
@Service
@Transactional
public class SituacaoLoteServiceImpl implements SituacaoLoteService{

    private final Logger log = LoggerFactory.getLogger(SituacaoLoteServiceImpl.class);
    
    @Inject
    private SituacaoLoteRepository situacaoLoteRepository;
    
    @Inject
    private SituacaoLoteSearchRepository situacaoLoteSearchRepository;
    
    /**
     * Save a situacaoLote.
     * 
     * @param situacaoLote the entity to save
     * @return the persisted entity
     */
    public SituacaoLote save(SituacaoLote situacaoLote) {
        log.debug("Request to save SituacaoLote : {}", situacaoLote);
        SituacaoLote result = situacaoLoteRepository.save(situacaoLote);
        situacaoLoteSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the situacaoLotes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<SituacaoLote> findAll() {
        log.debug("Request to get all SituacaoLotes");
        List<SituacaoLote> result = situacaoLoteRepository.findAll();
        return result;
    }

    /**
     *  Get one situacaoLote by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SituacaoLote findOne(Long id) {
        log.debug("Request to get SituacaoLote : {}", id);
        SituacaoLote situacaoLote = situacaoLoteRepository.findOne(id);
        return situacaoLote;
    }

    /**
     *  Delete the  situacaoLote by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SituacaoLote : {}", id);
        situacaoLoteRepository.delete(id);
        situacaoLoteSearchRepository.delete(id);
    }

    /**
     * Search for the situacaoLote corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SituacaoLote> search(String query) {
        log.debug("Request to search SituacaoLotes for query {}", query);
        return StreamSupport
            .stream(situacaoLoteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
