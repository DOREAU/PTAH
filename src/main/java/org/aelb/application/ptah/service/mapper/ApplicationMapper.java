package org.aelb.application.ptah.service.mapper;

import org.aelb.application.ptah.domain.*;
import org.aelb.application.ptah.service.dto.ApplicationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Application and its DTO ApplicationDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonneMapper.class})
public interface ApplicationMapper extends EntityMapper<ApplicationDTO, Application> {

    @Mapping(source = "responsable.id", target = "responsableId")
    ApplicationDTO toDto(Application application);

    @Mapping(source = "responsableId", target = "responsable")
    Application toEntity(ApplicationDTO applicationDTO);

    default Application fromId(Long id) {
        if (id == null) {
            return null;
        }
        Application application = new Application();
        application.setId(id);
        return application;
    }
}
