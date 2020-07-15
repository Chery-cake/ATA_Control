package com.control.ata.model.individual;

import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.tipo_pessoa.Juiz;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.model.torneio.Cronometro;
import com.control.ata.model.torneio.Placar;
import com.control.ata.model.torneio.Torneio;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class RingueIndividual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean fechado;
    private Integer numero;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Collection<Juiz> juiz;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Collection<Competidor> competidor;

    @ManyToOne
    private Torneio torneio;

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

    public RingueIndividual(Boolean fechado, Integer numero,
            Collection<Juiz> juiz, Collection<Competidor> competidor, Torneio torneio,
            Collection<CategoriaCompeticao> categoriaCompeticao) {
        this.fechado = fechado;
        this.numero = numero;
        this.juiz = juiz;
        this.competidor = competidor;
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

    public void setNumero(Integer numero) {
        this.numero = numero;
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
