package org.aelb.application.ptah.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CodeAcces.
 */
@Entity
@Table(name = "code_acces")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "codeacces")
public class CodeAcces implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @ManyToOne
    @JsonIgnoreProperties("contients")
    private Scenario scenario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public CodeAcces code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public CodeAcces scenario(Scenario scenario) {
        this.scenario = scenario;
        return this;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
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
        CodeAcces codeAcces = (CodeAcces) o;
        if (codeAcces.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), codeAcces.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CodeAcces{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            "}";
    }
}
