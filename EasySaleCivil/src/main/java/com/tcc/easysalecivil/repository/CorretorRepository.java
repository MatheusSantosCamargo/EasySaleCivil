package com.tcc.easysalecivil.repository;

import com.tcc.easysalecivil.domain.Corretor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Corretor entity.
 */
@SuppressWarnings("unused")
public interface CorretorRepository extends JpaRepository<Corretor,Long> {

}
