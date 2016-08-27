package com.tcc.easysalecivil.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Imobiliaria.
 */
@Entity
@Table(name = "imobiliaria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "imobiliaria")
public class Imobiliaria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "endereco", nullable = false)
    private String endereco;

    @NotNull
    @Column(name = "telefone", nullable = false)
    private String telefone;

    @NotNull
    @Column(name = "nome_responsavel", nullable = false)
    private String nomeResponsavel;

    @NotNull
    @Column(name = "creci_responsavel", nullable = false)
    private String creciResponsavel;

    @NotNull
    @Column(name = "ind_ativa", nullable = false)
    private Boolean indAtiva;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getCreciResponsavel() {
        return creciResponsavel;
    }

    public void setCreciResponsavel(String creciResponsavel) {
        this.creciResponsavel = creciResponsavel;
    }

    public Boolean isIndAtiva() {
        return indAtiva;
    }

    public void setIndAtiva(Boolean indAtiva) {
        this.indAtiva = indAtiva;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Imobiliaria imobiliaria = (Imobiliaria) o;
        if(imobiliaria.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, imobiliaria.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Imobiliaria{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", endereco='" + endereco + "'" +
            ", telefone='" + telefone + "'" +
            ", nomeResponsavel='" + nomeResponsavel + "'" +
            ", creciResponsavel='" + creciResponsavel + "'" +
            ", indAtiva='" + indAtiva + "'" +
            '}';
    }
}
