package org.aelb.application.ptah.service.mapper;

import org.aelb.application.ptah.domain.*;
import org.aelb.application.ptah.service.dto.PersonneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Personne and its DTO PersonneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonneMapper extends EntityMapper<PersonneDTO, Personne> {



    default Personne fromId(Long id) {
        if (id == null) {
            return null;
        }
        Personne personne = new Personne();
        personne.setId(id);
        return personne;
    }
}
