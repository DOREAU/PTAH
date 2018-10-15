package org.aelb.application.ptah.repository;

import org.aelb.application.ptah.domain.CodeAcces;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CodeAcces entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodeAccesRepository extends JpaRepository<CodeAcces, Long> {

}
