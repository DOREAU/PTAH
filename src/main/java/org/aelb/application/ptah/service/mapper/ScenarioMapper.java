package org.aelb.application.ptah.service.mapper;

import org.aelb.application.ptah.domain.*;
import org.aelb.application.ptah.service.dto.ScenarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Scenario and its DTO ScenarioDTO.
 */
@Mapper(componentModel = "spring", uses = {EquipeMapper.class})
public interface ScenarioMapper extends EntityMapper<ScenarioDTO, Scenario> {

    @Mapping(source = "estCreePourUn.id", target = "estCreePourUnId")
    ScenarioDTO toDto(Scenario scenario);

    @Mapping(target = "contients", ignore = true)
    @Mapping(target = "contients", ignore = true)
    @Mapping(source = "estCreePourUnId", target = "estCreePourUn")
    Scenario toEntity(ScenarioDTO scenarioDTO);

    default Scenario fromId(Long id) {
        if (id == null) {
            return null;
        }
        Scenario scenario = new Scenario();
        scenario.setId(id);
        return scenario;
    }
}
