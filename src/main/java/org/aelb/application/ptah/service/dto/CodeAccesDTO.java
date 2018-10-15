package org.aelb.application.ptah.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CodeAcces entity.
 */
public class CodeAccesDTO implements Serializable {

    private Long id;

    private String code;

    private Long scenarioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(Long scenarioId) {
        this.scenarioId = scenarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CodeAccesDTO codeAccesDTO = (CodeAccesDTO) o;
        if (codeAccesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), codeAccesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CodeAccesDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", scenario=" + getScenarioId() +
            "}";
    }
}
