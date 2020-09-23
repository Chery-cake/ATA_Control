package com.control.ata.model.individual;

import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.tipo_pessoa.Juiz;
import com.control.ata.model.torneio.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class RingueIndividual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean fechado;

    private Integer numeroRingue;
    private Integer numeroRodada;

    private String idade;
    private Integer nivel;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Collection<Juiz> juiz;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Collection<Competidor> competidor;

    @ManyToOne
    private Torneio torneio;

    @ManyToOne
    private RodadaJuiz rodadaJuiz;

    //private Collection<Torneio> torneio;

    @JsonIgnore
    @OneToMany(mappedBy = "ringueIndividual", cascade = CascadeType.ALL)
    private Collection<PlanilhaListaIndividual> planilhaListaIndividual;

    @JsonIgnore
    @OneToMany(mappedBy = "ringueIndividual", cascade = CascadeType.ALL)
    private Collection<PlanilhaChaveamentoIndividual> planilhaChaveamentoIndividual;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "ringueTime")
    private Cronometro cronometro;

    @ManyToOne
    private Placar placar;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Collection<CategoriaCompeticao> categoriaCompeticao;

    public RingueIndividual() {
    }

    public RingueIndividual(Boolean fechado, Integer numeroRingue, Integer numeroRodada, String idade, Integer nivel,
            Collection<Juiz> juiz, Torneio torneio, Collection<CategoriaCompeticao> categoriaCompeticao, RodadaJuiz rodadaJuiz) {
        this.fechado = fechado;
        this.numeroRingue = numeroRingue;
        this.numeroRodada = numeroRodada;
        this.idade = idade;
        this.nivel = nivel;
        this.juiz = juiz;
        this.torneio = torneio;
        this.categoriaCompeticao = categoriaCompeticao;
        this.rodadaJuiz = rodadaJuiz;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getFechado() {
        return fechado;
    }

    public Integer getNumeroRingue() {
        return numeroRingue;
    }

    public Integer getNumeroRodada() {
        return numeroRodada;
    }

    public String getIdade() {
        return idade;
    }

    public Integer getNivel() {
        return nivel;
    }

    public Collection<Competidor> getCompetidor() {
        return competidor;
    }

    public void setCompetidor(Collection<Competidor> competidor) {
        this.competidor = competidor;
    }

    public Collection<Juiz> getJuiz() {
        return juiz;
    }

    public void setJuiz(Collection<Juiz> juiz) {
        this.juiz = juiz;
    }

    public Torneio getTorneio() {
        return torneio;
    }

    public RodadaJuiz getRodadaJuiz() {
        return rodadaJuiz;
    }

    public Collection<PlanilhaListaIndividual> getPlanilhaListaIndividual() {
        return planilhaListaIndividual;
    }

    public void setPlanilhaListaIndividual(
            Collection<PlanilhaListaIndividual> planilhaListaIndividual) {
        this.planilhaListaIndividual = planilhaListaIndividual;
    }

    public Collection<PlanilhaChaveamentoIndividual> getPlanilhaChaveamentoIndividual() {
        return planilhaChaveamentoIndividual;
    }

    public void setPlanilhaChaveamentoIndividual(
            Collection<PlanilhaChaveamentoIndividual> planilhaChaveamentoIndividual) {
        this.planilhaChaveamentoIndividual = planilhaChaveamentoIndividual;
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
