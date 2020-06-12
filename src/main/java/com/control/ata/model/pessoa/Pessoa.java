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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "faixa_fk")
    private Faixa faixa;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_fk")
    private Endereco endereco;

}
