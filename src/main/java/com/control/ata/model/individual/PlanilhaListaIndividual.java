package com.control.ata.model.individual;

import com.control.ata.model.torneio.CategoriaCompeticao;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class PlanilhaListaIndividual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private CategoriaCompeticao categoriaCompeticao;

    @ManyToOne
    private RingueIndividual ringueIndividual;

    @JsonIgnore
    @OneToMany(mappedBy = "planilhaListaIndividual", cascade = CascadeType.ALL)
    private Collection<ChaveListaIndividual> chaveListaIndividualList;

    public PlanilhaListaIndividual() {
    }

    public PlanilhaListaIndividual(CategoriaCompeticao categoriaCompeticao,
            RingueIndividual ringueIndividual) {
        this.categoriaCompeticao = categoriaCompeticao;
        this.ringueIndividual = ringueIndividual;
    }

    public Integer getId() {
        return id;
    }

    public CategoriaCompeticao getCategoriaCompeticao() {
        return categoriaCompeticao;
    }

    public RingueIndividual getRingueIndividual() {
        return ringueIndividual;
    }

    public Collection<ChaveListaIndividual> getChaveListaIndividualList() {
        return chaveListaIndividualList;
    }

    public void setChaveListaIndividualList(
            Collection<ChaveListaIndividual> chaveListaIndividualList) {
        this.chaveListaIndividualList = chaveListaIndividualList;
    }
}
