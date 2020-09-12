package com.control.ata.model.torneio;

import com.control.ata.model.tipo_pessoa.Juiz;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
public class RodadaJuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String inicio;
    private String termino;
    private Date dia;

    @ManyToOne
    private Torneio torneio;

    @JsonIgnore
    @ManyToMany(mappedBy = "rodadaJuizList")
    private Collection<Juiz> juiz;

    public RodadaJuiz() {
    }

    public RodadaJuiz(String inicio, String termino, Date dia, Torneio torneio) {
        this.inicio = inicio;
        this.termino = termino;
        this.dia = dia;
        this.torneio = torneio;
    }

    public Integer getId() {
        return id;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public Torneio getTorneio() {
        return torneio;
    }

    public Collection<Juiz> getJuiz() {
        return juiz;
    }
}
