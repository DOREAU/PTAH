package org.aelb.application.ptah.repository;

import org.aelb.application.ptah.domain.PageWeb;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PageWeb entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PageWebRepository extends JpaRepository<PageWeb, Long> {

}
