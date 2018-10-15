package org.aelb.application.ptah.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PageWeb entity.
 */
public class PageWebDTO implements Serializable {

    private Long id;

    private String urlPage;

    private Long scenarioId;

    private Long pageDebutId;

    private Long pageFinId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlPage() {
        return urlPage;
    }

    public void setUrlPage(String urlPage) {
        this.urlPage = urlPage;
    }

    public Long getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(Long scenarioId) {
        this.scenarioId = scenarioId;
    }

    public Long getPageDebutId() {
        return pageDebutId;
    }

    public void setPageDebutId(Long scenarioId) {
        this.pageDebutId = scenarioId;
    }

    public Long getPageFinId() {
        return pageFinId;
    }

    public void setPageFinId(Long scenarioId) {
        this.pageFinId = scenarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PageWebDTO pageWebDTO = (PageWebDTO) o;
        if (pageWebDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pageWebDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PageWebDTO{" +
            "id=" + getId() +
            ", urlPage='" + getUrlPage() + "'" +
            ", scenario=" + getScenarioId() +
            ", pageDebut=" + getPageDebutId() +
            ", pageFin=" + getPageFinId() +
            "}";
    }
}
