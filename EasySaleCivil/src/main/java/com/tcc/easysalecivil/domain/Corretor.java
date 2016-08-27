package com.tcc.easysalecivil.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Corretor.
 */
@Entity
@Table(name = "corretor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "corretor")
public class Corretor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "creci", nullable = false)
    private String creci;

    @NotNull
    @Column(name = "primeiro_telefone", nullable = false)
    private String primeiroTelefone;

    @Column(name = "segundo_telefone")
    private String segundoTelefone;

    @NotNull
    @Column(name = "primeiro_email", nullable = false)
    private String primeiroEmail;

    @Column(name = "segundo_email")
    private String segundoEmail;

    @OneToMany(mappedBy = "id")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Imobiliaria> imobiliarias = new HashSet<>();

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

    public String getCreci() {
        return creci;
    }

    public void setCreci(String creci) {
        this.creci = creci;
    }

    public String getPrimeiroTelefone() {
        return primeiroTelefone;
    }

    public void setPrimeiroTelefone(String primeiroTelefone) {
        this.primeiroTelefone = primeiroTelefone;
    }

    public String getSegundoTelefone() {
        return segundoTelefone;
    }

    public void setSegundoTelefone(String segundoTelefone) {
        this.segundoTelefone = segundoTelefone;
    }

    public String getPrimeiroEmail() {
        return primeiroEmail;
    }

    public void setPrimeiroEmail(String primeiroEmail) {
        this.primeiroEmail = primeiroEmail;
    }

    public String getSegundoEmail() {
        return segundoEmail;
    }

    public void setSegundoEmail(String segundoEmail) {
        this.segundoEmail = segundoEmail;
    }

    public Set<Imobiliaria> getImobiliarias() {
        return imobiliarias;
    }

    public void setImobiliarias(Set<Imobiliaria> imobiliarias) {
        this.imobiliarias = imobiliarias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Corretor corretor = (Corretor) o;
        if(corretor.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, corretor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Corretor{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", creci='" + creci + "'" +
            ", primeiroTelefone='" + primeiroTelefone + "'" +
            ", segundoTelefone='" + segundoTelefone + "'" +
            ", primeiroEmail='" + primeiroEmail + "'" +
            ", segundoEmail='" + segundoEmail + "'" +
            '}';
    }
}
