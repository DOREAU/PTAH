package org.aelb.application.ptah.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PageWeb.
 */
@Entity
@Table(name = "page_web")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pageweb")
public class PageWeb implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "url_page")
    private String urlPage;

    @ManyToOne
    @JsonIgnoreProperties("contients")
    private Scenario scenario;

    @OneToMany(mappedBy = "pageWeb")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LienPageWeb> estContenueDans = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("")
    private Scenario pageDebut;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Scenario pageFin;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlPage() {
        return urlPage;
    }

    public PageWeb urlPage(String urlPage) {
        this.urlPage = urlPage;
        return this;
    }

    public void setUrlPage(String urlPage) {
        this.urlPage = urlPage;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public PageWeb scenario(Scenario scenario) {
        this.scenario = scenario;
        return this;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public Set<LienPageWeb> getEstContenueDans() {
        return estContenueDans;
    }

    public PageWeb estContenueDans(Set<LienPageWeb> lienPageWebs) {
        this.estContenueDans = lienPageWebs;
        return this;
    }

    public PageWeb addEstContenueDans(LienPageWeb lienPageWeb) {
        this.estContenueDans.add(lienPageWeb);
        lienPageWeb.setPageWeb(this);
        return this;
    }

    public PageWeb removeEstContenueDans(LienPageWeb lienPageWeb) {
        this.estContenueDans.remove(lienPageWeb);
        lienPageWeb.setPageWeb(null);
        return this;
    }

    public void setEstContenueDans(Set<LienPageWeb> lienPageWebs) {
        this.estContenueDans = lienPageWebs;
    }

    public Scenario getPageDebut() {
        return pageDebut;
    }

    public PageWeb pageDebut(Scenario scenario) {
        this.pageDebut = scenario;
        return this;
    }

    public void setPageDebut(Scenario scenario) {
        this.pageDebut = scenario;
    }

    public Scenario getPageFin() {
        return pageFin;
    }

    public PageWeb pageFin(Scenario scenario) {
        this.pageFin = scenario;
        return this;
    }

    public void setPageFin(Scenario scenario) {
        this.pageFin = scenario;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PageWeb pageWeb = (PageWeb) o;
        if (pageWeb.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pageWeb.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PageWeb{" +
            "id=" + getId() +
            ", urlPage='" + getUrlPage() + "'" +
            "}";
    }
}
