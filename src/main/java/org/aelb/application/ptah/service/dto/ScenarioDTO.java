package org.aelb.application.ptah.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Scenario entity.
 */
public class ScenarioDTO implements Serializable {

    private Long id;

    private String nom;

    private Long estCreePourUnId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getEstCreePourUnId() {
        return estCreePourUnId;
    }

    public void setEstCreePourUnId(Long equipeId) {
        this.estCreePourUnId = equipeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScenarioDTO scenarioDTO = (ScenarioDTO) o;
        if (scenarioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scenarioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScenarioDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", estCreePourUn=" + getEstCreePourUnId() +
            "}";
    }
}
