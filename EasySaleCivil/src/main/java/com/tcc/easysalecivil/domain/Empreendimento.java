package com.tcc.easysalecivil.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Empreendimento.
 */
@Entity
@Table(name = "empreendimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "empreendimento")
public class Empreendimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "localizacao", nullable = false)
    private String localizacao;

    @NotNull
    @Column(name = "ind_condominio_fechado", nullable = false)
    private Boolean indCondominioFechado;

    @NotNull
    @Column(name = "distancia_centro_cidade", precision=10, scale=2, nullable = false)
    private BigDecimal distanciaCentroCidade;

    @NotNull
    @Column(name = "caminho_imagem_empreendimento", nullable = false)
    private String caminhoImagemEmpreendimento;

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

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Boolean isIndCondominioFechado() {
        return indCondominioFechado;
    }

    public void setIndCondominioFechado(Boolean indCondominioFechado) {
        this.indCondominioFechado = indCondominioFechado;
    }

    public BigDecimal getDistanciaCentroCidade() {
        return distanciaCentroCidade;
    }

    public void setDistanciaCentroCidade(BigDecimal distanciaCentroCidade) {
        this.distanciaCentroCidade = distanciaCentroCidade;
    }

    public String getCaminhoImagemEmpreendimento() {
        return caminhoImagemEmpreendimento;
    }

    public void setCaminhoImagemEmpreendimento(String caminhoImagemEmpreendimento) {
        this.caminhoImagemEmpreendimento = caminhoImagemEmpreendimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Empreendimento empreendimento = (Empreendimento) o;
        if(empreendimento.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, empreendimento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Empreendimento{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", localizacao='" + localizacao + "'" +
            ", indCondominioFechado='" + indCondominioFechado + "'" +
            ", distanciaCentroCidade='" + distanciaCentroCidade + "'" +
            ", caminhoImagemEmpreendimento='" + caminhoImagemEmpreendimento + "'" +
            '}';
    }
}
