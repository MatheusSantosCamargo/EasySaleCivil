package com.tcc.easysalecivil.repository.search;

import com.tcc.easysalecivil.domain.Empreendimento;
import com.tcc.easysalecivil.domain.Lote;
import com.tcc.easysalecivil.domain.SituacaoLote;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Lote entity.
 */
public interface LoteSearchRepository extends ElasticsearchRepository<Lote, Long> {
	
	public List<Lote> findBySituacaoLoteIs(SituacaoLote idSituacao);
	
	public List<Lote> findByEmpreendimento(Empreendimento idEmpreendimento);
}
