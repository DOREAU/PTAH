package org.aelb.application.ptah.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Personne entity.
 */
public class PersonneDTO implements Serializable {

    private Long id;

    private String nom;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonneDTO personneDTO = (PersonneDTO) o;
        if(personneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonneDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
