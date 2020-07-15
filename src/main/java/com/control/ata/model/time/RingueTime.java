package com.control.ata.model.time;

import com.control.ata.model.tipo_pessoa.Juiz;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.model.torneio.Cronometro;
import com.control.ata.model.torneio.Placar;
import com.control.ata.model.torneio.Torneio;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class RingueTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean fechado;
    private Integer numero;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Collection<Juiz> juiz;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Collection<Time> time;

    @ManyToOne
    private Torneio torneio;

    //private Collection<Torneio> torneio;

    @JsonIgnore
    @OneToMany(mappedBy = "ringueTime", cascade = CascadeType.ALL)
    private Collection<PlanilhaListaTime> planilhaListaTime;

    @JsonIgnore
    @OneToMany(mappedBy = "ringueTime", cascade = CascadeType.ALL)
    private Collection<PlanilhaChaveamentoTime> planilhaChaveamentoTime;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "ringueTime")
    private Cronometro cronometro;

    @ManyToOne
    private Placar placar;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Collection<CategoriaCompeticao> categoriaCompeticao;

    public RingueTime() {
    }

    public RingueTime(Boolean fechado, Integer numero, Collection<Juiz> juiz, Collection<Time> time,
            Torneio torneio, Collection<CategoriaCompeticao> categoriaCompeticao) {
        this.fechado = fechado;
        this.numero = numero;
        this.juiz = juiz;
        this.time = time;
        this.torneio = torneio;
        this.categoriaCompeticao = categoriaCompeticao;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getFechado() {
        return fechado;
    }

    public Integer getNumero() {
        return numero;
    }

    public Collection<Juiz> getJuiz() {
        return juiz;
    }

    public void setJuiz(Collection<Juiz> juiz) {
        this.juiz = juiz;
    }

    public Collection<Time> getTime() {
        return time;
    }

    public void setTime(Collection<Time> time) {
        this.time = time;
    }

    public Torneio getTorneio() {
        return torneio;
    }

    public Collection<PlanilhaListaTime> getPlanilhaListaTime() {
        return planilhaListaTime;
    }

    public void setPlanilhaListaTime(Collection<PlanilhaListaTime> planilhaListaTime) {
        this.planilhaListaTime = planilhaListaTime;
    }

    public Collection<PlanilhaChaveamentoTime> getPlanilhaChaveamentoTime() {
        return planilhaChaveamentoTime;
    }

    public void setPlanilhaChaveamentoTime(
            Collection<PlanilhaChaveamentoTime> planilhaChaveamentoTime) {
        this.planilhaChaveamentoTime = planilhaChaveamentoTime;
    }

    public Cronometro getCronometro() {
        return cronometro;
    }

    public void setCronometro(Cronometro cronometro) {
        this.cronometro = cronometro;
    }

    public Placar getPlacar() {
        return placar;
    }

    public void setPlacar(Placar placar) {
        this.placar = placar;
    }

    public Collection<CategoriaCompeticao> getCategoriaCompeticao() {
        return categoriaCompeticao;
    }

    public void setCategoriaCompeticao(
            Collection<CategoriaCompeticao> categoriaCompeticao) {
        this.categoriaCompeticao = categoriaCompeticao;
    }
}
