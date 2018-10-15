package org.aelb.application.ptah.repository.search;

import org.aelb.application.ptah.domain.EtatCode;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EtatCode entity.
 */
public interface EtatCodeSearchRepository extends ElasticsearchRepository<EtatCode, Long> {
}
