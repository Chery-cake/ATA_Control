package com.control.ata.model.torneio;

import com.control.ata.model.endereco.Endereco;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.time.RingueTime;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.tipo_pessoa.Juiz;
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
    private Integer maxNumeroRingues;
    private Boolean pontuar;

    @ManyToOne
    private Endereco endereco;

    @ManyToOne
    private CategoriaTorneio categoriaTorneio;

    @OneToMany(mappedBy = "torneio", cascade = CascadeType.ALL)
    private Collection<Competidor> competidorCollection;

    @OneToMany(mappedBy = "torneio", cascade = CascadeType.ALL)
    private Collection<Juiz> juizCollection;

    @JsonIgnore
    @OneToMany(mappedBy = "torneio", cascade = CascadeType.ALL)
    private Collection<RodadaJuiz> rodadaJuizList;

    @JsonIgnore
    @OneToMany(mappedBy = "torneio", cascade = CascadeType.ALL)
    private Collection<RingueIndividual> ringueIndividualCollection;

    @JsonIgnore
    @OneToMany(mappedBy = "torneio", cascade = CascadeType.ALL)
    private Collection<RingueTime> ringueTimeCollection;

    public Torneio() {
    }

    public Torneio(Date dataInicio, Date dataTermino, Integer maxNumeroRingues, Boolean pontuar, Endereco endereco,
            CategoriaTorneio categoriaTorneio) {
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.maxNumeroRingues = maxNumeroRingues;
        this.pontuar = pontuar;
        this.endereco = endereco;
        this.categoriaTorneio = categoriaTorneio;
    }

    public Integer getId() {
        return id;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public Integer getMaxNumeroRingues() {
        return maxNumeroRingues;
    }

    public Boolean getPontuar() {
        return pontuar;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public CategoriaTorneio getCategoriaTorneio() {
        return categoriaTorneio;
    }
}
