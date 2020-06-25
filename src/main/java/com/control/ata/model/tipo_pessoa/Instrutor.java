package com.control.ata.model.tipo_pessoa;

import com.control.ata.model.Academia;
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
    @JoinColumn(name = "academia_fk")
    private Academia academia;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pessoa_fk")
    private Pessoa pessoa;

    @JsonIgnore
    @OneToMany(mappedBy = "instrutor")
    private Collection<Pessoa> alunos;

    public Instrutor() {
    }

    public Instrutor(Academia academia, Pessoa pessoa) {
        this.academia = academia;
        this.pessoa = pessoa;
    }

    public Academia getAcademia() {
        return academia;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }
}
