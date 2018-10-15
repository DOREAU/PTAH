package org.aelb.application.ptah.repository;

import org.aelb.application.ptah.domain.LienPageWeb;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LienPageWeb entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LienPageWebRepository extends JpaRepository<LienPageWeb, Long> {

}
