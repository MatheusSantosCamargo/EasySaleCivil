package com.tcc.easysalecivil.service;

import com.tcc.easysalecivil.domain.SituacaoLote;

import java.util.List;

/**
 * Service Interface for managing SituacaoLote.
 */
public interface SituacaoLoteService {

    /**
     * Save a situacaoLote.
     * 
     * @param situacaoLote the entity to save
     * @return the persisted entity
     */
    SituacaoLote save(SituacaoLote situacaoLote);

    /**
     *  Get all the situacaoLotes.
     *  
     *  @return the list of entities
     */
    List<SituacaoLote> findAll();

    /**
     *  Get the "id" situacaoLote.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    SituacaoLote findOne(Long id);

    /**
     *  Delete the "id" situacaoLote.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the situacaoLote corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    List<SituacaoLote> search(String query);
}
