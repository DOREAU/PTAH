package org.aelb.application.ptah.service.mapper;

import org.aelb.application.ptah.domain.*;
import org.aelb.application.ptah.service.dto.PageWebDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PageWeb and its DTO PageWebDTO.
 */
@Mapper(componentModel = "spring", uses = {ScenarioMapper.class})
public interface PageWebMapper extends EntityMapper<PageWebDTO, PageWeb> {

    @Mapping(source = "scenario.id", target = "scenarioId")
    @Mapping(source = "pageDebut.id", target = "pageDebutId")
    @Mapping(source = "pageFin.id", target = "pageFinId")
    PageWebDTO toDto(PageWeb pageWeb);

    @Mapping(source = "scenarioId", target = "scenario")
    @Mapping(target = "estContenueDans", ignore = true)
    @Mapping(source = "pageDebutId", target = "pageDebut")
    @Mapping(source = "pageFinId", target = "pageFin")
    PageWeb toEntity(PageWebDTO pageWebDTO);

    default PageWeb fromId(Long id) {
        if (id == null) {
            return null;
        }
        PageWeb pageWeb = new PageWeb();
        pageWeb.setId(id);
        return pageWeb;
    }
}
