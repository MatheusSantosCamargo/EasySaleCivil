package com.tcc.easysalecivil.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CadastroCliente.
 */
@Entity
@Table(name = "cadastro_cliente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cadastrocliente")
public class CadastroCliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Size(min = 11, max = 14)
    @Column(name = "cpf_cpnj", length = 14, nullable = false)
    private String cpf_cpnj;

    @Column(name = "rg")
    private String rg;

    @NotNull
    @Column(name = "rua", nullable = false)
    private String rua;

    @NotNull
    @Column(name = "bairro", nullable = false)
    private String bairro;

    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @NotNull
    @Column(name = "cidade", nullable = false)
    private String cidade;

    @NotNull
    @Column(name = "cep", nullable = false)
    private String cep;

    @NotNull
    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "celular")
    private String celular;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

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

    public String getCpf_cpnj() {
        return cpf_cpnj;
    }

    public void setCpf_cpnj(String cpf_cpnj) {
        this.cpf_cpnj = cpf_cpnj;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CadastroCliente cadastroCliente = (CadastroCliente) o;
        if(cadastroCliente.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cadastroCliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CadastroCliente{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", cpf_cpnj='" + cpf_cpnj + "'" +
            ", rg='" + rg + "'" +
            ", rua='" + rua + "'" +
            ", bairro='" + bairro + "'" +
            ", numero='" + numero + "'" +
            ", complemento='" + complemento + "'" +
            ", cidade='" + cidade + "'" +
            ", cep='" + cep + "'" +
            ", estado='" + estado + "'" +
            ", telefone='" + telefone + "'" +
            ", celular='" + celular + "'" +
            ", email='" + email + "'" +
            '}';
    }
}
