package com.control.ata.model.torneio;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class CategoriaTorneio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private Integer prioridade;

    @OneToMany(mappedBy = "categoriaTorneio", cascade = CascadeType.ALL)
    private Collection<Titulo> tituloList;

    @OneToMany(mappedBy = "categoriaTorneio", cascade = CascadeType.ALL)
    private Collection<Torneio> torneioList;

    public CategoriaTorneio() {
    }

    public CategoriaTorneio(String nome, Integer prioridade) {
        this.nome = nome;
        this.prioridade = prioridade;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getPrioridade() {
        return prioridade;
    }
}
