package org.aelb.application.ptah.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Equipe entity.
 */
public class EquipeDTO implements Serializable {

    private Long id;

    private String nom;

    private Long aUnId;

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

    public Long getAUnId() {
        return aUnId;
    }

    public void setAUnId(Long codeAccesId) {
        this.aUnId = codeAccesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EquipeDTO equipeDTO = (EquipeDTO) o;
        if (equipeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), equipeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EquipeDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", aUn=" + getAUnId() +
            "}";
    }
}
