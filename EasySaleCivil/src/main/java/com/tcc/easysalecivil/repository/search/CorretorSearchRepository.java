package com.tcc.easysalecivil.repository.search;

import com.tcc.easysalecivil.domain.Corretor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Corretor entity.
 */
public interface CorretorSearchRepository extends ElasticsearchRepository<Corretor, Long> {
}
