package com.control.ata.model.tipo_pessoa;

import com.control.ata.model.individual.ChaveLutaIndividual;
import com.control.ata.model.individual.PlanilhaListaIndividual;
import com.control.ata.model.individual.RankingIndividual;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.time.Time;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Competidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double peso;
    private Double altura;
    private String nivel;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pessoa_fk")
    private Pessoa pessoa;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private Collection<CategoriaCompeticao> categoriaCompeticao;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private Collection<Time> time;

    @JsonIgnore
    @OneToMany(mappedBy = "competidor")
    private Collection<PlanilhaListaIndividual> planilhaListaIndividualList;

    @JsonIgnore
    @OneToMany(mappedBy = "competidor")
    private Collection<RankingIndividual> rankingIndividualList;

    @ManyToMany(mappedBy = "competidor")
    private Collection<RingueIndividual> ringueIndividualCollection;

    @JsonIgnore
    @OneToMany(mappedBy = "competidorVermelho")
    private Collection<ChaveLutaIndividual> chaveLutaIndividualVermelho;

    @JsonIgnore
    @OneToMany(mappedBy = "competidorBranco")
    private Collection<ChaveLutaIndividual> chaveLutaIndividualBranco;

    public Competidor() {
    }

    public Competidor(Double peso, Double altura, String nivel, Pessoa pessoa,
            Collection<CategoriaCompeticao> categoriaCompeticao) {
        this.peso = peso;
        this.altura = altura;
        this.nivel = nivel;
        this.pessoa = pessoa;
        this.categoriaCompeticao = categoriaCompeticao;
    }

    public Competidor(Double peso, Double altura, String nivel, Pessoa pessoa,
            Collection<CategoriaCompeticao> categoriaCompeticao, Collection<Time> time) {
        this.peso = peso;
        this.altura = altura;
        this.nivel = nivel;
        this.pessoa = pessoa;
        this.categoriaCompeticao = categoriaCompeticao;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public Double getPeso() {
        return peso;
    }

    public Double getAltura() {
        return altura;
    }

    public String getNivel() {
        return nivel;
    }

    public Collection<CategoriaCompeticao> getCategoriaCompeticao() {
        return categoriaCompeticao;
    }

    public void setCategoriaCompeticao(
            Collection<CategoriaCompeticao> categoriaCompeticao) {
        this.categoriaCompeticao = categoriaCompeticao;
    }

    public Collection<Time> getTime() {
        return time;
    }

    public void setTime(Collection<Time> time) {
        this.time = time;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }
}
