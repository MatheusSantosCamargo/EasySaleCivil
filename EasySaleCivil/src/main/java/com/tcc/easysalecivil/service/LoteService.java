package com.tcc.easysalecivil.service;

import com.tcc.easysalecivil.domain.Empreendimento;
import com.tcc.easysalecivil.domain.Lote;
import com.tcc.easysalecivil.domain.SituacaoLote;

import java.util.List;

/**
 * Service Interface for managing Lote.
 */
public interface LoteService {

    /**
     * Save a lote.
     * 
     * @param lote the entity to save
     * @return the persisted entity
     */
    Lote save(Lote lote);

    /**
     *  Get all the lotes.
     *  
     *  @return the list of entities
     */
    List<Lote> findAll();

    /**
     *  Get the "id" lote.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Lote findOne(Long id);

    /**
     *  Delete the "id" lote.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the lote corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    List<Lote> search(String query);

	List<Lote> findBySituacaoLoteIs(SituacaoLote idSituacao);
	
	List<Lote> findByEmpreendimento(Empreendimento idEmpreendimento);
}
