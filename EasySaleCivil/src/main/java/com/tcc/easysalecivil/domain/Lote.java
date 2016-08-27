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
 * A Lote.
 */
@Entity
@Table(name = "lote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "lote")
public class Lote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @NotNull
    @Column(name = "rua", nullable = false)
    private String rua;

    @NotNull
    @Column(name = "tamanho_terreno", precision=10, scale=2, nullable = false)
    private BigDecimal tamanhoTerreno;

    @NotNull
    @Column(name = "tamanho_frente_terreno", precision=10, scale=2, nullable = false)
    private BigDecimal tamanhoFrenteTerreno;

    @NotNull
    @Column(name = "distancia_portaria", precision=10, scale=2, nullable = false)
    private BigDecimal distanciaPortaria;

    @Column(name = "distancia_esquina_mais_proxima", precision=10, scale=2)
    private BigDecimal distanciaEsquinaMaisProxima;

    @NotNull
    @Column(name = "elevacao_terreno", precision=10, scale=2, nullable = false)
    private BigDecimal elevacaoTerreno;

    @NotNull
    @Column(name = "posicao_relacao_ao_sol", nullable = false)
    private String posicaoRelacaoAoSol;

    @NotNull
    @Column(name = "distancia_area_lazer", precision=10, scale=2, nullable = false)
    private BigDecimal distanciaAreaLazer;

    @NotNull
    @Column(name = "quadra", nullable = false)
    private String quadra;

    @NotNull
    @Column(name = "valor_total", precision=10, scale=2, nullable = false)
    private BigDecimal valorTotal;

    @NotNull
    @Column(name = "valor_entrada", precision=10, scale=2, nullable = false)
    private BigDecimal valorEntrada;

    @NotNull
    @Column(name = "taxa_juros", precision=10, scale=2, nullable = false)
    private BigDecimal taxaJuros;

    @ManyToOne
    @NotNull
    private Imobiliaria imobiliaria;

    @ManyToOne
    @NotNull
    private Empreendimento empreendimento;

    @OneToOne
    @NotNull
    @JoinColumn(unique = true)
    private SituacaoLote situacaoLote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public BigDecimal getTamanhoTerreno() {
        return tamanhoTerreno;
    }

    public void setTamanhoTerreno(BigDecimal tamanhoTerreno) {
        this.tamanhoTerreno = tamanhoTerreno;
    }

    public BigDecimal getTamanhoFrenteTerreno() {
        return tamanhoFrenteTerreno;
    }

    public void setTamanhoFrenteTerreno(BigDecimal tamanhoFrenteTerreno) {
        this.tamanhoFrenteTerreno = tamanhoFrenteTerreno;
    }

    public BigDecimal getDistanciaPortaria() {
        return distanciaPortaria;
    }

    public void setDistanciaPortaria(BigDecimal distanciaPortaria) {
        this.distanciaPortaria = distanciaPortaria;
    }

    public BigDecimal getDistanciaEsquinaMaisProxima() {
        return distanciaEsquinaMaisProxima;
    }

    public void setDistanciaEsquinaMaisProxima(BigDecimal distanciaEsquinaMaisProxima) {
        this.distanciaEsquinaMaisProxima = distanciaEsquinaMaisProxima;
    }

    public BigDecimal getElevacaoTerreno() {
        return elevacaoTerreno;
    }

    public void setElevacaoTerreno(BigDecimal elevacaoTerreno) {
        this.elevacaoTerreno = elevacaoTerreno;
    }

    public String getPosicaoRelacaoAoSol() {
        return posicaoRelacaoAoSol;
    }

    public void setPosicaoRelacaoAoSol(String posicaoRelacaoAoSol) {
        this.posicaoRelacaoAoSol = posicaoRelacaoAoSol;
    }

    public BigDecimal getDistanciaAreaLazer() {
        return distanciaAreaLazer;
    }

    public void setDistanciaAreaLazer(BigDecimal distanciaAreaLazer) {
        this.distanciaAreaLazer = distanciaAreaLazer;
    }

    public String getQuadra() {
        return quadra;
    }

    public void setQuadra(String quadra) {
        this.quadra = quadra;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(BigDecimal valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public Imobiliaria getImobiliaria() {
        return imobiliaria;
    }

    public void setImobiliaria(Imobiliaria imobiliaria) {
        this.imobiliaria = imobiliaria;
    }

    public Empreendimento getEmpreendimento() {
        return empreendimento;
    }

    public void setEmpreendimento(Empreendimento empreendimento) {
        this.empreendimento = empreendimento;
    }

    public SituacaoLote getSituacaoLote() {
        return situacaoLote;
    }

    public void setSituacaoLote(SituacaoLote situacaoLote) {
        this.situacaoLote = situacaoLote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lote lote = (Lote) o;
        if(lote.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Lote{" +
            "id=" + id +
            ", codigo='" + codigo + "'" +
            ", rua='" + rua + "'" +
            ", tamanhoTerreno='" + tamanhoTerreno + "'" +
            ", tamanhoFrenteTerreno='" + tamanhoFrenteTerreno + "'" +
            ", distanciaPortaria='" + distanciaPortaria + "'" +
            ", distanciaEsquinaMaisProxima='" + distanciaEsquinaMaisProxima + "'" +
            ", elevacaoTerreno='" + elevacaoTerreno + "'" +
            ", posicaoRelacaoAoSol='" + posicaoRelacaoAoSol + "'" +
            ", distanciaAreaLazer='" + distanciaAreaLazer + "'" +
            ", quadra='" + quadra + "'" +
            ", valorTotal='" + valorTotal + "'" +
            ", valorEntrada='" + valorEntrada + "'" +
            ", taxaJuros='" + taxaJuros + "'" +
            '}';
    }
}
