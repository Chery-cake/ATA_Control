package com.control.ata.model.tipo_pessoa;

import com.control.ata.model.pessoa.Pessoa;

import javax.persistence.*;

@Entity
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    private Pessoa pessoa;

    public Administrador() {
    }

    public Administrador(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Integer getId() {
        return id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }
}
