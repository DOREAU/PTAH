package org.aelb.application.ptah.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of LienPageWebSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class LienPageWebSearchRepositoryMockConfiguration {

    @MockBean
    private LienPageWebSearchRepository mockLienPageWebSearchRepository;

}
