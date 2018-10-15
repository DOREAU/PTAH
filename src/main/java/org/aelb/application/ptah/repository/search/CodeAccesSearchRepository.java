package org.aelb.application.ptah.repository.search;

import org.aelb.application.ptah.domain.CodeAcces;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CodeAcces entity.
 */
public interface CodeAccesSearchRepository extends ElasticsearchRepository<CodeAcces, Long> {
}
