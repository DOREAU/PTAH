package org.aelb.application.ptah.repository.search;

import org.aelb.application.ptah.domain.LienPageWeb;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LienPageWeb entity.
 */
public interface LienPageWebSearchRepository extends ElasticsearchRepository<LienPageWeb, Long> {
}
