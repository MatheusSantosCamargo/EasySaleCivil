package com.tcc.easysalecivil.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SituacaoLote.
 */
@Entity
@Table(name = "situacao_lote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "situacaolote")
public class SituacaoLote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "situacao", nullable = false)
    private String situacao;

    @NotNull
    @Column(name = "cor", nullable = false)
    private String cor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SituacaoLote situacaoLote = (SituacaoLote) o;
        if(situacaoLote.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, situacaoLote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SituacaoLote{" +
            "id=" + id +
            ", situacao='" + situacao + "'" +
            ", cor='" + cor + "'" +
            '}';
    }
}
