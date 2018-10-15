package org.aelb.application.ptah.repository.search;

import org.aelb.application.ptah.domain.Scenario;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Scenario entity.
 */
public interface ScenarioSearchRepository extends ElasticsearchRepository<Scenario, Long> {
}
