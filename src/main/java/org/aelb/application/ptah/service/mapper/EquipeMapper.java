package org.aelb.application.ptah.service.mapper;

import org.aelb.application.ptah.domain.*;
import org.aelb.application.ptah.service.dto.EquipeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Equipe and its DTO EquipeDTO.
 */
@Mapper(componentModel = "spring", uses = {CodeAccesMapper.class})
public interface EquipeMapper extends EntityMapper<EquipeDTO, Equipe> {

    @Mapping(source = "aUn.id", target = "aUnId")
    EquipeDTO toDto(Equipe equipe);

    @Mapping(source = "aUnId", target = "aUn")
    Equipe toEntity(EquipeDTO equipeDTO);

    default Equipe fromId(Long id) {
        if (id == null) {
            return null;
        }
        Equipe equipe = new Equipe();
        equipe.setId(id);
        return equipe;
    }
}
