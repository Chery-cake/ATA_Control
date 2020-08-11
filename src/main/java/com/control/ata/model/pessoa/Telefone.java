package com.control.ata.model.pessoa;

import javax.persistence.*;

@Entity
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String telefone;

    private Boolean Celular;

    @ManyToOne
    private Pessoa pessoa;

    public Telefone() {
    }

    public Telefone(String telefone, Boolean celular, Pessoa pessoa) {
        this.telefone = telefone;
        Celular = celular;
        this.pessoa = pessoa;
    }

    public Integer getId() {
        return id;
    }

    public String getTelefone() {
        return telefone;
    }

    public Boolean getCelular() {
        return Celular;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }


}
