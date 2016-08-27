package com.tcc.easysalecivil.repository.search;

import com.tcc.easysalecivil.domain.CadastroCliente;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CadastroCliente entity.
 */
public interface CadastroClienteSearchRepository extends ElasticsearchRepository<CadastroCliente, Long> {
}
