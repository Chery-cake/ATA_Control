package com.control.ata.model.torneio;

import com.control.ata.model.tipo_pessoa.Juiz;

import javax.persistence.*;

@Entity
public class RodadaJuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Torneio torneio;

    @ManyToOne
    private Juiz juiz;

    public RodadaJuiz() {
    }

    public RodadaJuiz(Torneio torneio, Juiz juiz) {
        this.torneio = torneio;
        this.juiz = juiz;
    }

    public Integer getId() {
        return id;
    }

    public Torneio getTorneio() {
        return torneio;
    }

    public Juiz getJuiz() {
        return juiz;
    }
}
