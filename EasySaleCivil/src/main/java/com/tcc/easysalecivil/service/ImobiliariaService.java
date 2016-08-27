package com.tcc.easysalecivil.service;

import com.tcc.easysalecivil.domain.Imobiliaria;

import java.util.List;

/**
 * Service Interface for managing Imobiliaria.
 */
public interface ImobiliariaService {

    /**
     * Save a imobiliaria.
     * 
     * @param imobiliaria the entity to save
     * @return the persisted entity
     */
    Imobiliaria save(Imobiliaria imobiliaria);

    /**
     *  Get all the imobiliarias.
     *  
     *  @return the list of entities
     */
    List<Imobiliaria> findAll();

    /**
     *  Get the "id" imobiliaria.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Imobiliaria findOne(Long id);

    /**
     *  Delete the "id" imobiliaria.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the imobiliaria corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    List<Imobiliaria> search(String query);
}
