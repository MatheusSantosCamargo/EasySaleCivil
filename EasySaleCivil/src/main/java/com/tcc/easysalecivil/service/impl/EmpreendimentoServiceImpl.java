package com.tcc.easysalecivil.service.impl;

import com.tcc.easysalecivil.service.EmpreendimentoService;
import com.tcc.easysalecivil.domain.Empreendimento;
import com.tcc.easysalecivil.repository.EmpreendimentoRepository;
import com.tcc.easysalecivil.repository.search.EmpreendimentoSearchRepository;
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
 * Service Implementation for managing Empreendimento.
 */
@Service
@Transactional
public class EmpreendimentoServiceImpl implements EmpreendimentoService{

    private final Logger log = LoggerFactory.getLogger(EmpreendimentoServiceImpl.class);
    
    @Inject
    private EmpreendimentoRepository empreendimentoRepository;
    
    @Inject
    private EmpreendimentoSearchRepository empreendimentoSearchRepository;
    
    /**
     * Save a empreendimento.
     * 
     * @param empreendimento the entity to save
     * @return the persisted entity
     */
    public Empreendimento save(Empreendimento empreendimento) {
        log.debug("Request to save Empreendimento : {}", empreendimento);
        Empreendimento result = empreendimentoRepository.save(empreendimento);
        int posicaoContent = result.getCaminhoImagemEmpreendimento().indexOf("content");
        if (posicaoContent != -1){
        	String caminhoImagemTratado = "../" + result.getCaminhoImagemEmpreendimento().substring(posicaoContent).replace("\"", "").replace('\\', '/');
            result.setCaminhoImagemEmpreendimento(caminhoImagemTratado);	
        }
        empreendimentoSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the empreendimentos.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Empreendimento> findAll() {
        log.debug("Request to get all Empreendimentos");
        List<Empreendimento> result = empreendimentoRepository.findAll();
        return result;
    }

    /**
     *  Get one empreendimento by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Empreendimento findOne(Long id) {
        log.debug("Request to get Empreendimento : {}", id);
        Empreendimento empreendimento = empreendimentoRepository.findOne(id);
        return empreendimento;
    }

    /**
     *  Delete the  empreendimento by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Empreendimento : {}", id);
        empreendimentoRepository.delete(id);
        empreendimentoSearchRepository.delete(id);
    }

    /**
     * Search for the empreendimento corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Empreendimento> search(String query) {
        log.debug("Request to search Empreendimentos for query {}", query);
        return StreamSupport
            .stream(empreendimentoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
