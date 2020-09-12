package com.control.ata.model.tipo_pessoa;

import com.control.ata.model.endereco.Academia;
import com.control.ata.model.pessoa.Pessoa;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Instrutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Academia academia;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    private Pessoa pessoa;

    @JsonIgnore
    @OneToMany(mappedBy = "instrutor", cascade = CascadeType.ALL)
    private Collection<Pessoa> alunos;

    public Instrutor() {
    }

    public Instrutor(Academia academia, Pessoa pessoa) {
        this.academia = academia;
        this.pessoa = pessoa;
    }

    public Integer getId() {
        return id;
    }

    public Academia getAcademia() {
        return academia;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }
}
