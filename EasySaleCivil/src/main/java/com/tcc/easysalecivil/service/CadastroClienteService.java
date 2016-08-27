package com.tcc.easysalecivil.service;

import com.tcc.easysalecivil.domain.CadastroCliente;

import java.util.List;

/**
 * Service Interface for managing CadastroCliente.
 */
public interface CadastroClienteService {

    /**
     * Save a cadastroCliente.
     * 
     * @param cadastroCliente the entity to save
     * @return the persisted entity
     */
    CadastroCliente save(CadastroCliente cadastroCliente);

    /**
     *  Get all the cadastroClientes.
     *  
     *  @return the list of entities
     */
    List<CadastroCliente> findAll();

    /**
     *  Get the "id" cadastroCliente.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    CadastroCliente findOne(Long id);

    /**
     *  Delete the "id" cadastroCliente.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the cadastroCliente corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    List<CadastroCliente> search(String query);
}
