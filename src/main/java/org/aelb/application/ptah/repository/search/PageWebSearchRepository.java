package org.aelb.application.ptah.repository.search;

import org.aelb.application.ptah.domain.PageWeb;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PageWeb entity.
 */
public interface PageWebSearchRepository extends ElasticsearchRepository<PageWeb, Long> {
}
