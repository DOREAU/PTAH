package org.aelb.application.ptah.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A EtatCode.
 */
@Entity
@Table(name = "etat_code")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "etatcode")
public class EtatCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_key")
    private Integer key;

    @Column(name = "jhi_value")
    private String value;

    @ManyToOne
    @JsonIgnoreProperties("")
    private CodeAcces aUnEtatDonne;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getKey() {
        return key;
    }

    public EtatCode key(Integer key) {
        this.key = key;
        return this;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public EtatCode value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CodeAcces getAUnEtatDonne() {
        return aUnEtatDonne;
    }

    public EtatCode aUnEtatDonne(CodeAcces codeAcces) {
        this.aUnEtatDonne = codeAcces;
        return this;
    }

    public void setAUnEtatDonne(CodeAcces codeAcces) {
        this.aUnEtatDonne = codeAcces;
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
        EtatCode etatCode = (EtatCode) o;
        if (etatCode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etatCode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EtatCode{" +
            "id=" + getId() +
            ", key=" + getKey() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
