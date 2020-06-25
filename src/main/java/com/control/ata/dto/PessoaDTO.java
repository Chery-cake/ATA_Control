package com.control.ata.dto;

import com.control.ata.model.pessoa.Faixa;
import com.control.ata.model.pessoa.Telefone;

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
    private Collection<Telefone> telefoneCollection;
    private Faixa faixa;
    private EnderecoDTO endereco;

}
