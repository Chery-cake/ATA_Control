package com.control.ata.model.pessoa;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Faixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "faixa")
    private Collection<Pessoa> pessoa;

    public Faixa() {
    }

    public Faixa(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Faixa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                //", pessoa=" + pessoa +
                '}';
    }
}
