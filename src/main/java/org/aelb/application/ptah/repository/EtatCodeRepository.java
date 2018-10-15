package org.aelb.application.ptah.repository;

import org.aelb.application.ptah.domain.EtatCode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EtatCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtatCodeRepository extends JpaRepository<EtatCode, Long> {

}
