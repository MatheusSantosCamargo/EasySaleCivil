package com.tcc.easysalecivil.service;

import com.tcc.easysalecivil.domain.Corretor;

import java.util.List;

/**
 * Service Interface for managing Corretor.
 */
public interface CorretorService {

    /**
     * Save a corretor.
     * 
     * @param corretor the entity to save
     * @return the persisted entity
     */
    Corretor save(Corretor corretor);

    /**
     *  Get all the corretors.
     *  
     *  @return the list of entities
     */
    List<Corretor> findAll();

    /**
     *  Get the "id" corretor.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Corretor findOne(Long id);

    /**
     *  Delete the "id" corretor.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the corretor corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    List<Corretor> search(String query);
}
