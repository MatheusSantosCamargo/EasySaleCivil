package com.tcc.easysalecivil.repository.search;

import com.tcc.easysalecivil.domain.SituacaoLote;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SituacaoLote entity.
 */
public interface SituacaoLoteSearchRepository extends ElasticsearchRepository<SituacaoLote, Long> {
}
