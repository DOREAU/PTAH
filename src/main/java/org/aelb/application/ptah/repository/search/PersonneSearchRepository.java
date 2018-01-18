package org.aelb.application.ptah.repository.search;

import org.aelb.application.ptah.domain.Personne;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Personne entity.
 */
public interface PersonneSearchRepository extends ElasticsearchRepository<Personne, Long> {
}
