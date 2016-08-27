package com.tcc.easysalecivil.repository;

import com.tcc.easysalecivil.domain.Empreendimento;
import com.tcc.easysalecivil.domain.Lote;
import com.tcc.easysalecivil.domain.SituacaoLote;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Lote entity.
 */
@SuppressWarnings("unused")
public interface LoteRepository extends JpaRepository<Lote,Long> {
	
	public List<Lote> findBySituacaoLoteIs(SituacaoLote idSituacao);
	
	public List<Lote> findByEmpreendimento(Empreendimento idEmpreendimento);
}
