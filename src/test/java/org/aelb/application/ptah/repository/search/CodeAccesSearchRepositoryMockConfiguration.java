package org.aelb.application.ptah.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of CodeAccesSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CodeAccesSearchRepositoryMockConfiguration {

    @MockBean
    private CodeAccesSearchRepository mockCodeAccesSearchRepository;

}
