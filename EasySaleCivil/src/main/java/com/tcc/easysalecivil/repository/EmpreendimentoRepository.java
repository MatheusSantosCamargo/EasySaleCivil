package com.tcc.easysalecivil.repository;

import com.tcc.easysalecivil.domain.Empreendimento;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Empreendimento entity.
 */
@SuppressWarnings("unused")
public interface EmpreendimentoRepository extends JpaRepository<Empreendimento,Long> {

}
