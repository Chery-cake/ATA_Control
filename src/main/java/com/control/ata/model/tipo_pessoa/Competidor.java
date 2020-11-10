package com.control.ata.model.tipo_pessoa;

import com.control.ata.model.individual.ChaveListaIndividual;
import com.control.ata.model.individual.ChaveLutaIndividual;
import com.control.ata.model.individual.RingueIndividual;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.time.Time;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.model.torneio.Titulo;
import com.control.ata.model.torneio.Torneio;
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
    private Integer nivel;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    private Pessoa pessoa;

    @ManyToOne
    private Torneio torneio;

    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<CategoriaCompeticao> categoriaCompeticao;

    @ManyToMany(mappedBy = "competidores")
    private Collection<Time> time;

    @JsonIgnore
    @OneToMany(mappedBy = "competidor", cascade = CascadeType.ALL)
    private Collection<ChaveListaIndividual> chaveListaIndividualList;

    @ManyToMany(mappedBy = "competidor")
    private Collection<RingueIndividual> ringueIndividualCollection;

    @JsonIgnore
    @OneToMany(mappedBy = "competidorVermelho", cascade = CascadeType.ALL)
    private Collection<ChaveLutaIndividual> chaveLutaIndividualVermelho;

    @JsonIgnore
    @OneToMany(mappedBy = "competidorBranco", cascade = CascadeType.ALL)
    private Collection<ChaveLutaIndividual> chaveLutaIndividualBranco;

    @JsonIgnore
    @OneToMany(mappedBy = "competidor", cascade = CascadeType.ALL)
    private Collection<Titulo> tituloList;

    public Competidor() {
    }

    public Competidor(Double peso, Double altura, Integer nivel, Pessoa pessoa, Torneio torneio,
            Collection<CategoriaCompeticao> categoriaCompeticao) {
        this.peso = peso;
        this.altura = altura;
        this.nivel = nivel;
        this.pessoa = pessoa;
        this.torneio = torneio;
        this.categoriaCompeticao = categoriaCompeticao;
    }

    public Competidor(Double peso, Double altura, Integer nivel, Pessoa pessoa, Torneio torneio,
            Collection<CategoriaCompeticao> categoriaCompeticao, Collection<Time> time) {
        this.peso = peso;
        this.altura = altura;
        this.nivel = nivel;
        this.pessoa = pessoa;
        this.torneio = torneio;
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

    public Integer getNivel() {
        return nivel;
    }

    public Torneio getTorneio() {
        return torneio;
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

    public Collection<Titulo> getTituloList() {
        return tituloList;
    }

    public void setTituloList(Collection<Titulo> tituloList) {
        this.tituloList = tituloList;
    }
}
