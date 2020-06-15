package com.control.ata.model.pessoa;

import com.control.ata.model.endereco.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String sobrenome;

    private Boolean genero;

    private Date dataNascimento;

    private String nomeUsuario;

    private String senha;

    private Integer status;

    private String foto;

    private String ataNumberWorld;

    private String ataNumberBrasil;

    @JsonIgnore
    @OneToMany(mappedBy = "pessoa")
    private Collection<Telefone> telefoneCollection;

    @ManyToOne
    @JoinColumn(name = "faixa_fk")
    private Faixa faixa;

    @ManyToOne
    @JoinColumn(name = "endereco_fk")
    private Endereco endereco;

    public Pessoa() {
    }

    public Pessoa(String nome, String sobrenome, Boolean genero, Date dataNascimento, String nomeUsuario, String senha,
                  Integer status, String foto, String ataNumberWorld, String ataNumberBrasil, Faixa faixa, Endereco endereco) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.status = status;
        this.foto = foto;
        this.ataNumberWorld = ataNumberWorld;
        this.ataNumberBrasil = ataNumberBrasil;
        this.faixa = faixa;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Boolean getGenero() {
        return genero;
    }

    public void setGenero(Boolean genero) {
        this.genero = genero;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getAtaNumberWorld() {
        return ataNumberWorld;
    }

    public void setAtaNumberWorld(String ataNumberWorld) {
        this.ataNumberWorld = ataNumberWorld;
    }

    public String getAtaNumberBrasil() {
        return ataNumberBrasil;
    }

    public void setAtaNumberBrasil(String ataNumberBrasil) {
        this.ataNumberBrasil = ataNumberBrasil;
    }

    public Collection<Telefone> getTelefoneCollection() {
        return telefoneCollection;
    }

    public void setTelefoneCollection(Collection<Telefone> telefoneCollection) {
        this.telefoneCollection = telefoneCollection;
    }

    public Faixa getFaixa() {
        return faixa;
    }

    public void setFaixa(Faixa faixa) {
        this.faixa = faixa;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", genero=" + genero +
                ", dataNascimento=" + dataNascimento +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", senha='" + senha + '\'' +
                ", status=" + status +
                ", foto='" + foto + '\'' +
                ", ataNumberWorld='" + ataNumberWorld + '\'' +
                ", ataNumberBrasil='" + ataNumberBrasil + '\'' +
                //", telefoneCollection=" + telefoneCollection +
                ", faixa=" + faixa +
                ", endereco=" + endereco +
                '}';
    }
}
