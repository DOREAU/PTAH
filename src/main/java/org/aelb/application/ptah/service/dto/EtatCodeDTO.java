package org.aelb.application.ptah.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EtatCode entity.
 */
public class EtatCodeDTO implements Serializable {

    private Long id;

    private Integer key;

    private String value;

    private Long aUnEtatDonneId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getAUnEtatDonneId() {
        return aUnEtatDonneId;
    }

    public void setAUnEtatDonneId(Long codeAccesId) {
        this.aUnEtatDonneId = codeAccesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EtatCodeDTO etatCodeDTO = (EtatCodeDTO) o;
        if (etatCodeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etatCodeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EtatCodeDTO{" +
            "id=" + getId() +
            ", key=" + getKey() +
            ", value='" + getValue() + "'" +
            ", aUnEtatDonne=" + getAUnEtatDonneId() +
            "}";
    }
}
