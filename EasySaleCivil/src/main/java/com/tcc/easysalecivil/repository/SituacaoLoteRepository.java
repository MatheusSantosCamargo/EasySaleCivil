package com.tcc.easysalecivil.repository;

import com.tcc.easysalecivil.domain.SituacaoLote;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SituacaoLote entity.
 */
@SuppressWarnings("unused")
public interface SituacaoLoteRepository extends JpaRepository<SituacaoLote,Long> {

}
