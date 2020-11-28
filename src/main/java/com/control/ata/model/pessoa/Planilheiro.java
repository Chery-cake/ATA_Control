package com.control.ata.model.pessoa;

import com.control.ata.model.torneio.Torneio;
import com.control.ata.security.entity.Usuario;

import javax.persistence.*;

@Entity
public class Planilheiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Torneio torneio;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "planilheiro")
    private Usuario usuario;

    public Planilheiro() {
    }

    public Planilheiro(Torneio torneio) {
        this.torneio = torneio;
    }

    public Integer getId() {
        return id;
    }

    public Torneio getTorneio() {
        return torneio;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
