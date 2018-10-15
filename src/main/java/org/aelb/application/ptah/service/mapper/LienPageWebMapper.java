package org.aelb.application.ptah.service.mapper;

import org.aelb.application.ptah.domain.*;
import org.aelb.application.ptah.service.dto.LienPageWebDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LienPageWeb and its DTO LienPageWebDTO.
 */
@Mapper(componentModel = "spring", uses = {PageWebMapper.class})
public interface LienPageWebMapper extends EntityMapper<LienPageWebDTO, LienPageWeb> {

    @Mapping(source = "pageWeb.id", target = "pageWebId")
    LienPageWebDTO toDto(LienPageWeb lienPageWeb);

    @Mapping(source = "pageWebId", target = "pageWeb")
    LienPageWeb toEntity(LienPageWebDTO lienPageWebDTO);

    default LienPageWeb fromId(Long id) {
        if (id == null) {
            return null;
        }
        LienPageWeb lienPageWeb = new LienPageWeb();
        lienPageWeb.setId(id);
        return lienPageWeb;
    }
}
