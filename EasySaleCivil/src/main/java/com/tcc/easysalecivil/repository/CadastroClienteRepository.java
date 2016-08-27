package com.tcc.easysalecivil.repository;

import com.tcc.easysalecivil.domain.CadastroCliente;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CadastroCliente entity.
 */
@SuppressWarnings("unused")
public interface CadastroClienteRepository extends JpaRepository<CadastroCliente,Long> {

}
