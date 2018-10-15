package org.aelb.application.ptah.repository.search;

import org.aelb.application.ptah.domain.Equipe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Equipe entity.
 */
public interface EquipeSearchRepository extends ElasticsearchRepository<Equipe, Long> {
}
