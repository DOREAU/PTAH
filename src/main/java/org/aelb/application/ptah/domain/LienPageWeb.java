package org.aelb.application.ptah.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LienPageWeb.
 */
@Entity
@Table(name = "lien_page_web")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "lienpageweb")
public class LienPageWeb implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_saisi")
    private String codeSaisi;

    @Column(name = "url_cible")
    private String urlCible;

    @ManyToOne
    @JsonIgnoreProperties("estContenueDans")
    private PageWeb pageWeb;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeSaisi() {
        return codeSaisi;
    }

    public LienPageWeb codeSaisi(String codeSaisi) {
        this.codeSaisi = codeSaisi;
        return this;
    }

    public void setCodeSaisi(String codeSaisi) {
        this.codeSaisi = codeSaisi;
    }

    public String getUrlCible() {
        return urlCible;
    }

    public LienPageWeb urlCible(String urlCible) {
        this.urlCible = urlCible;
        return this;
    }

    public void setUrlCible(String urlCible) {
        this.urlCible = urlCible;
    }

    public PageWeb getPageWeb() {
        return pageWeb;
    }

    public LienPageWeb pageWeb(PageWeb pageWeb) {
        this.pageWeb = pageWeb;
        return this;
    }

    public void setPageWeb(PageWeb pageWeb) {
        this.pageWeb = pageWeb;
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
        LienPageWeb lienPageWeb = (LienPageWeb) o;
        if (lienPageWeb.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lienPageWeb.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LienPageWeb{" +
            "id=" + getId() +
            ", codeSaisi='" + getCodeSaisi() + "'" +
            ", urlCible='" + getUrlCible() + "'" +
            "}";
    }
}
