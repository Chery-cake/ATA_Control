package com.control.ata.model.time;

import com.control.ata.model.torneio.CategoriaCompeticao;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class PlanilhaListaTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private RingueTime ringueTime;

    @ManyToOne
    private CategoriaCompeticao categoriaCompeticao;

    @JsonIgnore
    @OneToMany(mappedBy = "planilhaListaTime", cascade = CascadeType.ALL)
    private Collection<ChaveListaTime> chaveListaTimeList;

    public PlanilhaListaTime() {
    }

    public PlanilhaListaTime(RingueTime ringueTime, CategoriaCompeticao categoriaCompeticao) {
        this.ringueTime = ringueTime;
        this.categoriaCompeticao = categoriaCompeticao;
    }

    public Integer getId() {
        return id;
    }

    public RingueTime getRingueTime() {
        return ringueTime;
    }

    public CategoriaCompeticao getCategoriaCompeticao() {
        return categoriaCompeticao;
    }

    public Collection<ChaveListaTime> getChaveListaTimeList() {
        return chaveListaTimeList;
    }

    public void setChaveListaTimeList(Collection<ChaveListaTime> ChaveListaTimeList) {
        this.chaveListaTimeList = ChaveListaTimeList;
    }
}