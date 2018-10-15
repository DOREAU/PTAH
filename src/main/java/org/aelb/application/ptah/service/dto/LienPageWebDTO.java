package org.aelb.application.ptah.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the LienPageWeb entity.
 */
public class LienPageWebDTO implements Serializable {

    private Long id;

    private String codeSaisi;

    private String urlCible;

    private Long pageWebId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeSaisi() {
        return codeSaisi;
    }

    public void setCodeSaisi(String codeSaisi) {
        this.codeSaisi = codeSaisi;
    }

    public String getUrlCible() {
        return urlCible;
    }

    public void setUrlCible(String urlCible) {
        this.urlCible = urlCible;
    }

    public Long getPageWebId() {
        return pageWebId;
    }

    public void setPageWebId(Long pageWebId) {
        this.pageWebId = pageWebId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LienPageWebDTO lienPageWebDTO = (LienPageWebDTO) o;
        if (lienPageWebDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lienPageWebDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LienPageWebDTO{" +
            "id=" + getId() +
            ", codeSaisi='" + getCodeSaisi() + "'" +
            ", urlCible='" + getUrlCible() + "'" +
            ", pageWeb=" + getPageWebId() +
            "}";
    }
}
