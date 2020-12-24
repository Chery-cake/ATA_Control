package com.control.ata.model.torneio;

import com.control.ata.model.individual.*;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class CategoriaCompeticao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private Boolean tipoChave;
    private Boolean tipoTime;
    private Integer limiteTempo;
    private Integer limitePonto;

    private Integer minimoMasculino;
    private Integer minimoFeminino;
    private Integer maximoTotal;

    @JsonIgnore
    @OneToMany(mappedBy = "categoriaCompeticao", cascade = CascadeType.ALL)
    private Collection<PlanilhaListaIndividual> planilhaListaIndividualList;

    @JsonIgnore
    @OneToMany(mappedBy = "categoriaCompeticao", cascade = CascadeType.ALL)
    private Collection<PlanilhaChaveamentoIndividual> planilhaChaveamentoIndividualList;

    @JsonIgnore
    @OneToMany(mappedBy = "categoriaCompeticao", cascade = CascadeType.ALL)
    private Collection<RankingIndividual> rankingIndividualList;

    @JsonIgnore
    @ManyToMany(mappedBy = "categoriaCompeticao")
    private Collection<Competidor> competidorList;

    @ManyToMany(mappedBy = "categoriaCompeticao")
    private Collection<RingueIndividual> ringueIndividualList;

    @JsonIgnore
    @OneToMany(mappedBy = "categoriaCompeticao", cascade = CascadeType.ALL)
    private Collection<Titulo> tituloList;

    @JsonIgnore
    @ManyToMany(mappedBy = "categoriaCompeticao")
    private Collection<ListaCategoriaCompetidorFechada> listaCategoriaCompetidorFechadaList;

    public CategoriaCompeticao() {
    }

    public CategoriaCompeticao(String nome, Boolean tipoChave, Boolean tipoTime, Integer limiteTempo,
            Integer limitePonto, Integer minimoMasculino, Integer minimoFeminino, Integer maximoTotal) {
        this.nome = nome;
        this.tipoChave = tipoChave;
        this.tipoTime = tipoTime;
        this.limiteTempo = limiteTempo;
        this.limitePonto = limitePonto;
        this.minimoMasculino = minimoMasculino;
        this.minimoFeminino = minimoFeminino;
        this.maximoTotal = maximoTotal;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Boolean getTipoChave() {
        return tipoChave;
    }

    public Boolean getTipoTime() {
        return tipoTime;
    }

    public Integer getLimiteTempo() {
        return limiteTempo;
    }

    public Integer getLimitePonto() {
        return limitePonto;
    }

    public Integer getMinimoMasculino() {
        return minimoMasculino;
    }

    public Integer getMinimoFeminino() {
        return minimoFeminino;
    }

    public Integer getMaximoTotal() {
        return maximoTotal;
    }
}