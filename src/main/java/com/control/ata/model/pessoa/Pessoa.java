package com.control.ata.model.pessoa;

import com.control.ata.model.endereco.Endereco;
import com.control.ata.model.individual.RankingIndividual;
import com.control.ata.model.tipo_pessoa.*;
import com.control.ata.security.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String sobrenome;
    private Boolean genero;
    private Date dataNascimento;
    private Integer status;
    private String ataNumberWorld;
    private String ataNumberBrasil;
    private Boolean isInstrutor;
    private String telefone;

    @ManyToOne
    private Faixa faixa;

    @ManyToOne
    private Endereco endereco;

    @ManyToOne
    private Instrutor instrutor;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "pessoa")
    private Competidor competidor;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "pessoa")
    private Instrutor IsInstrutor;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "pessoa")
    private Juiz juiz;

    @JsonIgnore
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private Collection<RankingIndividual> rankingIndividualList;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "pessoa")
    private Usuario usuario;

    public Pessoa() {
    }

    public Pessoa(String nome, String sobrenome, Boolean genero, Date dataNascimento, Integer status,
            String ataNumberWorld, String ataNumberBrasil, Boolean isInstrutor,
            String telefone, Faixa faixa, Endereco endereco, Usuario usuario) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.status = status;
        this.ataNumberWorld = ataNumberWorld;
        this.ataNumberBrasil = ataNumberBrasil;
        this.isInstrutor = isInstrutor;
        this.telefone = telefone;
        this.faixa = faixa;
        this.endereco = endereco;
        this.usuario = usuario;
    }

    public Pessoa(String nome, String sobrenome, Boolean genero, Date dataNascimento, Integer status,
            String ataNumberWorld, String ataNumberBrasil, Boolean isInstrutor, String telefone, Faixa faixa,
            Endereco endereco, Instrutor instrutor, Usuario usuario) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.status = status;
        this.ataNumberWorld = ataNumberWorld;
        this.ataNumberBrasil = ataNumberBrasil;
        this.isInstrutor = isInstrutor;
        this.telefone = telefone;
        this.faixa = faixa;
        this.endereco = endereco;
        this.instrutor = instrutor;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Boolean getIsInstrutor() {
        return isInstrutor;
    }

    public void setIsInstrutor(Boolean isInstrutor) {
        isInstrutor = isInstrutor;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(Instrutor instrutor) {
        this.instrutor = instrutor;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Collection<RankingIndividual> getRankingIndividualList() {
        return rankingIndividualList;
    }

    public void setRankingIndividualList(
            Collection<RankingIndividual> rankingIndividualList) {
        this.rankingIndividualList = rankingIndividualList;
    }
}
