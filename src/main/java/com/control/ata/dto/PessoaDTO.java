package com.control.ata.dto;

import com.control.ata.security.entity.Usuario;

import java.util.Date;

public class PessoaDTO {

    private Integer id;
    private String nome;
    private String sobrenome;
    private Boolean genero;
    private Date dataNascimento;
    private Integer status;
    private String foto;
    private String ataNumberWorld;
    private String ataNumberBrasil;
    private Boolean isInstrutor;
    private String telefone;
    private Integer faixa;
    private EnderecoDTO enderecoDTO;
    private Integer instrutor;
    private Integer academia;
    private Usuario usuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setIsInstrutor(Boolean instrutor) {
        isInstrutor = instrutor;
    }

    public Integer getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(Integer instrutor) {
        this.instrutor = instrutor;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Integer getFaixa() {
        return faixa;
    }

    public void setFaixa(Integer faixa) {
        this.faixa = faixa;
    }

    public EnderecoDTO getEnderecoDTO() {
        return enderecoDTO;
    }

    public void setEnderecoDTO(EnderecoDTO enderecoDTO) {
        this.enderecoDTO = enderecoDTO;
    }

    public Integer getAcademia() {
        return academia;
    }

    public void setAcademia(Integer academia) {
        this.academia = academia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "PessoaDTO{" +
                "nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", genero=" + genero +
                ", dataNascimento=" + dataNascimento +
                ", status=" + status +
                ", foto='" + foto + '\'' +
                ", ataNumberWorld='" + ataNumberWorld + '\'' +
                ", ataNumberBrasil='" + ataNumberBrasil + '\'' +
                ", isInstrutor=" + isInstrutor +
                ", telefone=" + telefone +
                ", faixa=" + faixa +
                ", enderecoDTO=" + enderecoDTO +
                ", instrutor=" + instrutor +
                ", academia=" + academia +
                ", usuario=" + usuario +
                '}';
    }
}
