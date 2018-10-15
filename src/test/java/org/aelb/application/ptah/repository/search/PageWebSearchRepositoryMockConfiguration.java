package org.aelb.application.ptah.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of PageWebSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PageWebSearchRepositoryMockConfiguration {

    @MockBean
    private PageWebSearchRepository mockPageWebSearchRepository;

}
