package com.tcc.easysalecivil.repository.search;

import com.tcc.easysalecivil.domain.Imobiliaria;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Imobiliaria entity.
 */
public interface ImobiliariaSearchRepository extends ElasticsearchRepository<Imobiliaria, Long> {
}
