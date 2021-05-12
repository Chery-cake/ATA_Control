package com.control.ata.model.torneio;

import com.control.ata.model.individual.RingueIndividual;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
public class Placar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tipo_plan;
    private Integer id_plan;
    private Integer id_chave;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private RingueIndividual ringueIndividual;

    public Placar() {
    }

    public Placar(RingueIndividual ringueIndividual) {
        this.ringueIndividual = ringueIndividual;
    }

    public Integer getId() {
        return id;
    }

    public String getTipo_plan() {
        return tipo_plan;
    }

    public void setTipo_plan(String tipo_plan) {
        this.tipo_plan = tipo_plan;
    }

    public Integer getId_plan() {
        return id_plan;
    }

    public void setId_plan(Integer id_plan) {
        this.id_plan = id_plan;
    }

    public Integer getId_chave() {
        return id_chave;
    }

    public void setId_chave(Integer id_chave) {
        this.id_chave = id_chave;
    }

    public RingueIndividual getRingueIndividual() {
        return ringueIndividual;
    }
}
