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
 * A Scenario.
 */
@Entity
@Table(name = "scenario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "scenario")
public class Scenario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @OneToMany(mappedBy = "scenario")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PageWeb> contients = new HashSet<>();
    @OneToMany(mappedBy = "scenario")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CodeAcces> contients = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("")
    private Equipe estCreePourUn;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Scenario nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<PageWeb> getContients() {
        return contients;
    }

    public Scenario contients(Set<PageWeb> pageWebs) {
        this.contients = pageWebs;
        return this;
    }

    public Scenario addContient(PageWeb pageWeb) {
        this.contients.add(pageWeb);
        pageWeb.setScenario(this);
        return this;
    }

    public Scenario removeContient(PageWeb pageWeb) {
        this.contients.remove(pageWeb);
        pageWeb.setScenario(null);
        return this;
    }

    public void setContients(Set<PageWeb> pageWebs) {
        this.contients = pageWebs;
    }

    public Set<CodeAcces> getContients() {
        return contients;
    }

    public Scenario contients(Set<CodeAcces> codeAcces) {
        this.contients = codeAcces;
        return this;
    }

    public Scenario addContient(CodeAcces codeAcces) {
        this.contients.add(codeAcces);
        codeAcces.setScenario(this);
        return this;
    }

    public Scenario removeContient(CodeAcces codeAcces) {
        this.contients.remove(codeAcces);
        codeAcces.setScenario(null);
        return this;
    }

    public void setContients(Set<CodeAcces> codeAcces) {
        this.contients = codeAcces;
    }

    public Equipe getEstCreePourUn() {
        return estCreePourUn;
    }

    public Scenario estCreePourUn(Equipe equipe) {
        this.estCreePourUn = equipe;
        return this;
    }

    public void setEstCreePourUn(Equipe equipe) {
        this.estCreePourUn = equipe;
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
        Scenario scenario = (Scenario) o;
        if (scenario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scenario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Scenario{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
