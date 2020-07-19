package com.control.ata.model.individual;

import com.control.ata.model.torneio.CategoriaCompeticao;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class PlanilhaChaveamentoIndividual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @OneToMany(mappedBy = "planilhaChaveamentoIndividual", cascade = CascadeType.ALL)
    private Collection<ChaveLutaIndividual> chaveLutaIndividual;

    @ManyToOne
    private CategoriaCompeticao categoriaCompeticao;

    @ManyToOne
    private RingueIndividual ringueIndividual;

    public PlanilhaChaveamentoIndividual() {
    }

    public PlanilhaChaveamentoIndividual(CategoriaCompeticao categoriaCompeticao,
            RingueIndividual ringueIndividual) {
        this.categoriaCompeticao = categoriaCompeticao;
        this.ringueIndividual = ringueIndividual;
    }

    public Integer getId() {
        return id;
    }

    public Collection<ChaveLutaIndividual> getChaveLutaIndividual() {
        return chaveLutaIndividual;
    }

    public void setChaveLutaIndividual(
            Collection<ChaveLutaIndividual> chaveLutaIndividual) {
        this.chaveLutaIndividual = chaveLutaIndividual;
    }

    public CategoriaCompeticao getCategoriaCompeticao() {
        return categoriaCompeticao;
    }

    public RingueIndividual getRingueIndividual() {
        return ringueIndividual;
    }

}
