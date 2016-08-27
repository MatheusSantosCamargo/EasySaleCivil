package com.tcc.easysalecivil.repository.search;

import com.tcc.easysalecivil.domain.Empreendimento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Empreendimento entity.
 */
public interface EmpreendimentoSearchRepository extends ElasticsearchRepository<Empreendimento, Long> {
}
