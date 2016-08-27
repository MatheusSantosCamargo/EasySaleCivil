package com.tcc.easysalecivil.repository;

import com.tcc.easysalecivil.domain.Imobiliaria;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Imobiliaria entity.
 */
@SuppressWarnings("unused")
public interface ImobiliariaRepository extends JpaRepository<Imobiliaria,Long> {

}
