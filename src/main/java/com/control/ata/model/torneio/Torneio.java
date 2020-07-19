package com.control.ata.model.torneio;

import com.control.ata.model.endereco.Endereco;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.time.RingueTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
public class Torneio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date dataInicio;
    private Date dataTermino;

    @ManyToOne
    private Endereco endereco;

    @JsonIgnore
    @OneToMany(mappedBy = "torneio", cascade = CascadeType.ALL)
    private Collection<RodadaJuiz> rodadaJuizList;

    @JsonIgnore
    @OneToMany(mappedBy = "torneio", cascade = CascadeType.ALL)
    private Collection<RingueIndividual> ringueIndividualCollection;

    @JsonIgnore
    @OneToMany(mappedBy = "torneio", cascade = CascadeType.ALL)
    private Collection<RingueTime> ringueTimeCollection;

    @ManyToOne
    private CategoriaTorneio categoriaTorneio;

    public Torneio() {
    }

    public Torneio(Date dataInicio, Date dataTermino, Endereco endereco, CategoriaTorneio categoriaTorneio) {
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.endereco = endereco;
        this.categoriaTorneio = categoriaTorneio;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public CategoriaTorneio getCategoriaTorneio() {
        return categoriaTorneio;
    }
}
