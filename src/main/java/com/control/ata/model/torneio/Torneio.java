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
    @JoinColumn(name = "endereco_fk")
    private Endereco endereco;

    @JsonIgnore
    @OneToMany(mappedBy = "torneio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<RodadaJuiz> rodadaJuizList;

    @JsonIgnore
    @OneToMany(mappedBy = "torneio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<RingueIndividual> ringueIndividualCollection;

    @JsonIgnore
    @OneToMany(mappedBy = "torneio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<RingueTime> ringueTimeCollection;

    public Torneio() {
    }

    public Torneio(Date dataInicio, Date dataTermino, Endereco endereco) {
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.endereco = endereco;
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
}
