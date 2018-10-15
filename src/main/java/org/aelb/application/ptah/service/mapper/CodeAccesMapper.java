package org.aelb.application.ptah.service.mapper;

import org.aelb.application.ptah.domain.*;
import org.aelb.application.ptah.service.dto.CodeAccesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CodeAcces and its DTO CodeAccesDTO.
 */
@Mapper(componentModel = "spring", uses = {ScenarioMapper.class})
public interface CodeAccesMapper extends EntityMapper<CodeAccesDTO, CodeAcces> {

    @Mapping(source = "scenario.id", target = "scenarioId")
    CodeAccesDTO toDto(CodeAcces codeAcces);

    @Mapping(source = "scenarioId", target = "scenario")
    CodeAcces toEntity(CodeAccesDTO codeAccesDTO);

    default CodeAcces fromId(Long id) {
        if (id == null) {
            return null;
        }
        CodeAcces codeAcces = new CodeAcces();
        codeAcces.setId(id);
        return codeAcces;
    }
}
