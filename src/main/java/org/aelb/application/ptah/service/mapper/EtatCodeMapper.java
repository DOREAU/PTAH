package org.aelb.application.ptah.service.mapper;

import org.aelb.application.ptah.domain.*;
import org.aelb.application.ptah.service.dto.EtatCodeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EtatCode and its DTO EtatCodeDTO.
 */
@Mapper(componentModel = "spring", uses = {CodeAccesMapper.class})
public interface EtatCodeMapper extends EntityMapper<EtatCodeDTO, EtatCode> {

    @Mapping(source = "aUnEtatDonne.id", target = "aUnEtatDonneId")
    EtatCodeDTO toDto(EtatCode etatCode);

    @Mapping(source = "aUnEtatDonneId", target = "aUnEtatDonne")
    EtatCode toEntity(EtatCodeDTO etatCodeDTO);

    default EtatCode fromId(Long id) {
        if (id == null) {
            return null;
        }
        EtatCode etatCode = new EtatCode();
        etatCode.setId(id);
        return etatCode;
    }
}
