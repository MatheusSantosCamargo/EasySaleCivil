package com.tcc.easysalecivil.service.impl;

import com.tcc.easysalecivil.service.LoteService;
import com.tcc.easysalecivil.domain.Empreendimento;
import com.tcc.easysalecivil.domain.Lote;
import com.tcc.easysalecivil.domain.SituacaoLote;
import com.tcc.easysalecivil.repository.LoteRepository;
import com.tcc.easysalecivil.repository.search.LoteSearchRepository;
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
 * Service Implementation for managing Lote.
 */
@Service
@Transactional
public class LoteServiceImpl implements LoteService{

    private final Logger log = LoggerFactory.getLogger(LoteServiceImpl.class);
    
    @Inject
    private LoteRepository loteRepository;
    
    @Inject
    private LoteSearchRepository loteSearchRepository;
    
    /**
     * Save a lote.
     * 
     * @param lote the entity to save
     * @return the persisted entity
     */
    public Lote save(Lote lote) {
        log.debug("Request to save Lote : {}", lote);
        Lote result = loteRepository.save(lote);
        loteSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the lotes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Lote> findAll() {
        log.debug("Request to get all Lotes");
        List<Lote> result = loteRepository.findAll();
        return result;
    }

    /**
     *  Get one lote by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Lote findOne(Long id) {
        log.debug("Request to get Lote : {}", id);
        Lote lote = loteRepository.findOne(id);
        return lote;
    }

    /**
     *  Delete the  lote by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Lote : {}", id);
        loteRepository.delete(id);
        loteSearchRepository.delete(id);
    }

    /**
     * Search for the lote corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Lote> search(String query) {
        log.debug("Request to search Lotes for query {}", query);
        return StreamSupport
            .stream(loteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

	public List<Lote> findBySituacaoLoteIs(SituacaoLote idSituacao) {
		log.debug("Recuperando todos os lotes com situacao DISPONIVEL");
		List<Lote> lotes = loteRepository.findBySituacaoLoteIs(idSituacao);
		return lotes;
	}
	
	public List<Lote> findByEmpreendimento(Empreendimento idEmpreendimento) {
		log.debug("Recuperando todos os lotes para o empreendimento" + idEmpreendimento);
		List<Lote> lotes = loteRepository.findByEmpreendimento(idEmpreendimento);
		return lotes;
	}
}

