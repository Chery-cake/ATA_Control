package com.control.ata.dto;

import com.control.ata.model.pessoa.Faixa;
import com.control.ata.model.pessoa.Telefone;
import com.control.ata.model.tipo_pessoa.Instrutor;

import java.util.Collection;
import java.util.Date;

public class PessoaDTO {

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
    private Boolean isInstrutor;
    private Collection<Telefone> telefoneCollection;
    private Faixa faixa;
    private EnderecoDTO endereco;
    private Instrutor instrutor;

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

    public Boolean getIsInstrutor() {
        return isInstrutor;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(Instrutor instrutor) {
        this.instrutor = instrutor;
    }

    public void setIsInstrutor(Boolean instrutor) {
        isInstrutor = instrutor;
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

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }
}
