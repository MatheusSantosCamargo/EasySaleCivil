package com.tcc.easysalecivil.service;

import com.tcc.easysalecivil.domain.Empreendimento;

import java.util.List;

/**
 * Service Interface for managing Empreendimento.
 */
public interface EmpreendimentoService {

    /**
     * Save a empreendimento.
     * 
     * @param empreendimento the entity to save
     * @return the persisted entity
     */
    Empreendimento save(Empreendimento empreendimento);

    /**
     *  Get all the empreendimentos.
     *  
     *  @return the list of entities
     */
    List<Empreendimento> findAll();

    /**
     *  Get the "id" empreendimento.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Empreendimento findOne(Long id);

    /**
     *  Delete the "id" empreendimento.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the empreendimento corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    List<Empreendimento> search(String query);
}
